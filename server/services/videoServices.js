import fs from 'fs';
import path from 'path';
import { spawn } from 'child_process';
import { v4 as uuidv4 } from 'uuid';
import { jobs } from '../utils/jobManager.js';

const VIDEO_DIR = path.join(process.cwd(), 'server', 'videos');
const OUTPUT_DIR = path.join(process.cwd(), 'processor', 'sampleOutput');
const JAR_PATH = path.join(process.cwd(), 'processor', 'target', 'centroid-finder-1.0-SNAPSHOT.jar');

export const getVideoList = () => {
    const files = fs.readdirSync(VIDEO_DIR);
    return files;
};

export const processVideoJob = (filename, targetColor, threshold) => {
    const inputPath = path.join(VIDEO_DIR, filename);
    const jobId = uuidv4();
    const outputPath = path.join(OUTPUT_DIR, `${jobId}_${filename}.csv`);

    const child = spawn('java', ['-jar', JAR_PATH, inputPath, outputPath, targetColor, threshold], {
        detached: true,
        stdio: 'ignore'
    });

    jobs[jobId] = { status: 'processing', outputPath };
    child.unref();

    return { jobId };
};


export const getJobStatus = (req, res) => {
    const { jobId } = req.params;
    const job = jobs[jobId];

    if (!job) return res.status(404).json({ error: "Job ID not found" });

    try {
        if (fs.existsSync(job.outputPath)) job.status = 'done';
        res.status(200).json({
            status: job.status,
            result: job.status === 'done' ? job.outputPath : undefined
        });
    } catch (err) {
        res.status(500).json({ error: 'Error fetching job status' });
    }
};
