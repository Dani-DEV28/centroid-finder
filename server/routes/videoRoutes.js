import { Router } from 'express';
import { getVideos, getThumbnail, processVideo, getStatus, checkJar, binarizerImg, getImage} from '../controllers/videoController.js';

const router = Router();

router.post('/process/:filename', processVideo);
router.post('/img/:filename', binarizerImg);

router.get('/api/health', checkJar);
router.get('/videos', getVideos);
router.get('/img/:filename', getImage);
router.get('/thumbnail/:filename', getThumbnail);
router.get('/process/:jobId/status', getStatus);

export default router;
