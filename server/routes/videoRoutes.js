import { Router } from 'express';
import { getVideos, getThumbnail, processVideo, getStatus } from '../controllers/videoController.js';

const router = Router();

router.get('/api/videos', getVideos);
router.get('/thumbnail/:filename', getThumbnail);
router.get('/process/:filename', processVideo);
router.get('/process/:jobId/status', getStatus);

export default router;
