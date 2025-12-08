import express from 'express';
import cors from 'cors';
import path from 'path';
import videoRouter from './routes/videoRoutes.js';

const VIDEO_DIR = process.env.VIDEO_DIR || '/default/video/path';
const RESULTS_DIR = process.env.RESULTS_DIR || '/default/results/path';

const app = express();
const PORT = 3000;

app.use(cors());

app.use(express.static('public'));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use('/videos', express.static(VIDEO_DIR));
app.use('/results', express.static(RESULTS_DIR));

app.use('/', videoRouter);

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});