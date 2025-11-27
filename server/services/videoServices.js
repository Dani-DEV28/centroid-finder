import fs from 'fs';
// import path from 'path';
import { spawn } from 'child_process';
import { v4 as uuidv4 } from 'uuid';
import { jobs } from '../utils/jobManager.js';
import path from 'path';

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
    const inputPath = path.join(VIDEO_DIR, filename);
    const jobId = uuidv4();
    const outputPath = path.join(RESULTS_DIR, `${jobId}.csv`);

    jobs[jobId] = { status: "processing", outputPath, logs: [] };

    if (!fs.existsSync(inputPath)) {
        jobs[jobId].status = "error";
        jobs[jobId].error = `Input file does not exist: ${inputPath}`;
        return { jobId };
    }
    
    const child = spawn("java", ["-jar", JAR_PATH, inputPath, targetColor, threshold, outputPath]);

    // if (!child) {
    //     throw new Error("Failed to spawn Java process");
    // }

    // Safety: child process failed to start
    child.on("error", (err) => {
        console.error("JAR failed to start:", err);
        jobs[jobId].status = "error";
        jobs[jobId].error = err.message;
    });

    // Capture stdout
    child.stdout.on("data", (data) => {
        const msg = data.toString();
        console.log(`JAR stdout: ${msg}`);
        jobs[jobId].logs.push(msg);
    });

    // Capture stderr
    child.stderr.on("data", (data) => {
        const msg = data.toString();
        console.error(`JAR stderr: ${msg}`);
        jobs[jobId].logs.push(msg);
    });

    // Wait for JAR to exit and CSV to exist
    child.on("exit", async (code, signal) => {

        // Catch JavaCV segfaults
        if (signal) {
            jobs[jobId].status = "error";
            jobs[jobId].error = `JAR crashed with signal ${signal}`;
            return;
        }

        const waitForFile = async (file, retries = 20, delayMs = 200) => {
            for (let i = 0; i < retries; i++) {
                if (fs.existsSync(file)) return true;
                await new Promise(r => setTimeout(r, delayMs));
            }
            return false;
        };

        const fileExists = await waitForFile(outputPath);
        if (code === 0 && fileExists) {
            jobs[jobId].status = "done";
        } else {
            jobs[jobId].status = "error";
            jobs[jobId].error = `JAR exited with code ${code}, CSV exists? ${fileExists}`;
        }
    });

    return { jobId };
};

export const getJobStatus = (jobId) => {
    const job = jobs[jobId];

    if (!job) return null;

    try {
        // If the job has an output file and exists, mark as done
        if (job.outputPath && fs.existsSync(job.outputPath)) {
            job.status = "done";
        }
        return {
            status: job.status,
            result: job.status === "done" ? job.outputPath : undefined,
            error: job.status === "error" ? job.error : undefined
        };
    } catch (err) {
        return { status: "error", error: "Error fetching job status" };
    }
};

export const jobsStore = jobs;
