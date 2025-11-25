import fs from 'fs';
// import path from 'path';
import { spawn } from 'child_process';
import { v4 as uuidv4 } from 'uuid';
import { jobs } from '../utils/jobManager.js';

const JAR_PATH = process.env.JAR_PATH; // "/app/app.jar"
const VIDEO_DIR = process.env.VIDEO_DIR;
const RESULTS_DIR = process.env.RESULTS_DIR;


// export const checkJar = () => {
//     try {
//         // Synchronously run `java -jar <JAR_PATH>` with no arguments to test if it executes
//         const result = spawnSync('java', ['-jar', JAR_PATH], { encoding: 'utf-8' });

//         if (result.error) {
//             return { status: 'error', message: result.error.message };
//         }

//         // If the process prints usage info, consider it "ok"
//         if (result.stdout.includes('Usage:') || result.stderr.includes('Usage:')) {
//             return { status: 'ok', message: 'Java JAR is accessible and executable' };
//         }

//         // Any unexpected output can be returned for debugging
//         return { status: 'ok', message: 'Java JAR executed successfully', stdout: result.stdout, stderr: result.stderr };
//     } catch (err) {
//         return { status: 'error', message: err.message };
//     }
// };

export const getVideoList = () => {
    const files = fs.readdirSync(VIDEO_DIR);
    return files;
};

export const processVideoJob = (filename, targetColor, threshold) => {
    const jobId = uuidv4();
    const inputPath = `${VIDEO_DIR}/${filename}`;
    const outputPath = `${RESULTS_DIR}/${jobId}.csv`;

    const child = spawn("java", [
        "-jar", JAR_PATH,
        inputPath,
        targetColor,
        threshold,
        outputPath
    ], {
        detached: true,
        stdio: "ignore"
    });

    jobs[jobId] = { status: "processing", outputPath };
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
