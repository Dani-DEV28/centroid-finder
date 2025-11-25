import { getVideoList, processVideoJob, getJobStatus } from '../services/videoServices.js';

export const getVideos = (req, res) => {
    try {
        const videos = getVideoList();
        res.status(200).json(videos);
    } catch (err) {
        res.status(500).json({ error: "Error reading video directory" });
    }
};

export const getThumbnail = (req, res) => {
    res.json({ message: 'No Thumbnail' });
};

export const processVideo = (req, res) => {
    const { filename } = req.params;
    const { targetColor, threshold } = req.query;

    if (!targetColor || !threshold) {
        return res.status(400).json({ error: "Missing targetColor or threshold query parameter." });
    }

    try {
        const job = processVideoJob(filename, targetColor, threshold);
        res.status(202).json(job); // 202 Accepted
    } catch (err) {
        res.status(500).json({ error: "Error starting job" });
    }
};

export const getStatus = (req, res) => {
    const { jobId } = req.params;
    const status = getJobStatus(jobId);
    if (!status) return res.status(404).json({ error: 'Job not found' });
    res.json(status);
};
