//Daniel - generate AI

import express from "express";
import { spawn } from "child_process";
import path from "path";
import { createJob, completeJob, failJob, getJobStatus } from "../utils/jobManager.js";

const router = express.Router();
// const videoDir = process.env.VIDEO_DIR;
const jarPath = process.env.JAR_PATH;
const resultsDir = process.env.RESULTS_DIR;

// POST /process/:filename?targetColor=ff0000&threshold=120
router.post("/:filename", (req, res) => {
  const { filename } = req.params;
  const { targetColor, threshold } = req.query;

  if (!targetColor || !threshold) {
    return res.status(400).json({ error: "Missing targetColor or threshold query parameter." });
  }

  const resultPath = path.join(resultsDir, `${filename}.csv`);
  const jobId = createJob({ filename, targetColor, threshold, resultPath });

  // Spawn the Java JAR as detached process
  const child = spawn("java", ["-jar", jarPath, filename, targetColor, threshold, resultPath], {
    detached: true,
    stdio: "ignore"
  });

  child.unref(); // Allow child to run independently

  // When process exits, update job status
  child.on("exit", code => {
    if (code === 0) completeJob(jobId, resultPath);
    else failJob(jobId, `JAR exited with code ${code}`);
  });

  res.status(202).json({ jobId });
});

// GET /process/:jobId/status
router.get("/:jobId/status", (req, res) => {
  const { jobId } = req.params;
  const job = getJobStatus(jobId);

  if (!job) return res.status(404).json({ error: "Job ID not found" });

  if (job.status === "done") {
    return res.json({ status: "done", result: `/results/${path.basename(job.resultPath)}` });
  } else if (job.status === "error") {
    return res.json({ status: "error", error: job.error });
  } else {
    return res.json({ status: "processing" });
  }
});

export default router;
