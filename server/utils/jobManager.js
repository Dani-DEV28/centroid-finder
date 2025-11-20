import { v4 as uuidv4 } from "uuid";

//Daniel - generate AI

const jobs = {}; // in-memory job store

export function createJob({ filename, targetColor, threshold, resultPath }) {
  const jobId = uuidv4();
  jobs[jobId] = {
    status: "processing",
    filename,
    targetColor,
    threshold,
    resultPath: resultPath || null,
    error: null,
  };
  return jobId;
}

export function completeJob(jobId, resultPath) {
  if (jobs[jobId]) {
    jobs[jobId].status = "done";
    jobs[jobId].resultPath = resultPath;
  }
}

export function failJob(jobId, errorMessage) {
  if (jobs[jobId]) {
    jobs[jobId].status = "error";
    jobs[jobId].error = errorMessage;
  }
}

export function getJobStatus(jobId) {
  if (!jobs[jobId]) return null;
  return jobs[jobId];
}
