//Daniel - generate AI

import express from "express";
import ffmpeg from "fluent-ffmpeg";
import path from "path";

const router = express.Router();
const videoDir = process.env.VIDEO_DIR;

// GET /thumbnail/:filename
router.get("/:filename", (req, res) => {
  const { filename } = req.params;
  const videoPath = path.join(videoDir, filename);

  res.type("jpeg");

  ffmpeg(videoPath)
    .screenshots({
      count: 1,
      timemarks: ["0"], // first frame
      filename: "thumbnail.jpg",
      folder: "/tmp" // temporary folder
    })
    .on("end", function() {
      res.sendFile("/tmp/thumbnail.jpg", err => {
        if (err) res.status(500).json({ error: "Error generating thumbnail" });
      });
    })
    .on("error", function(err) {
      console.error(err);
      res.status(500).json({ error: "Error generating thumbnail" });
    });
});

export default router;