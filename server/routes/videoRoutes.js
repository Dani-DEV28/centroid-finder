import { Router } from 'express';
import { getVideos, getThumbnail, processVideo, getStatus, checkJar, binarizerImg, getImage, getResults} from '../controllers/videoController.js';

const router = Router();

router.post('/process/:filename', processVideo);

router.get('/api/health', checkJar);
router.get('/videos', getVideos);
router.get('/process', getResults);
// router.get('/img/:filename', getImage); // Removed as per new design, as not being used
router.get('/thumbnail/:filename', getThumbnail);
router.get('/process/:jobId/status', getStatus);

export default router;
