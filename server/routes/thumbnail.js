import express from 'express';
import fs from 'fs';
import path from 'path';
import ffmpeg from 'fluent-ffmpeg';
import { tmpdir } from 'os';

const router = express.Router();

// Directory where your videos are stored
const VIDEOS_DIR = path.join(process.cwd(), 'videos');

router.get('/:filename', (req, res) => {
  const { filename } = req.params;
  const videoPath = path.join(VIDEOS_DIR, filename);

  // Check if file exists
  if (!fs.existsSync(videoPath)) {
    return res.status(404).json({ error: 'Video file not found' });
  }

  // Temporary file for thumbnail
  const tmpFile = path.join(tmpdir(), `thumb-${Date.now()}.jpg`);

  // Use ffmpeg to grab first frame
  ffmpeg(videoPath)
    .frames(1) // grab only first frame
    .output(tmpFile)
    .on('end', () => {
      res.sendFile(tmpFile, (err) => {
        if (err) console.error(err);
        // Clean up temporary file after sending
        fs.unlink(tmpFile, () => {});
      });
    })
    .on('error', (err) => {
      console.error(err);
      res.status(500).json({ error: 'Error generating thumbnail' });
    })
    .run();
});

export default router;
