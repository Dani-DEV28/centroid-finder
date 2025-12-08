import { processVideoJob, getJobStatus, getList } from '../services/videoServices.js';
import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import path from "path";
import crypto from "crypto";

const videoDir = process.env.VIDEO_DIR;

export const getVideos = (req, res) => {
    try {
        const videos = getList(1);
        res.status(200).json(videos);
    } catch (err) {
        res.status(500).json({ error: "Error reading video directory" });
    }
};

export const getResults = (req, res) => {
    try {
        const result = getList(0);
        res.status(200).json(result);
    } catch (err) {
        res.status(500).json({ error: "Error reading result directory" });
    }
};

export const getImage = (req, res) => {
    const { filename } = req.params;

    const imagePath = path.join(videoDir, filename);

    if (!fs.existsSync(imagePath)) {
        return res.status(404).json({ error: "Image not found" });
    }
    
    try {
        res.status(200).json(imagePath);
    } catch (err) {
        res.status(500).json({ error: "Error reading video directory" });
    }
}

export const checkJar = (req, res) => {
  try {
    if (!fs.existsSync(process.env.JAR_PATH))
      return res.status(500).json({ status: "missing JAR" });

    res.status(200).json({ status: "ready", jar: process.env.JAR_PATH });
  } catch (err) {
    res.status(500).json({ status: "error", message: err.message });
  }
};

export const getThumbnail = (req, res) => {
    const { filename } = req.params;

    // Full path to the video
    const videoPath = path.join(videoDir, filename);

    // Check video exists
    if (!fs.existsSync(videoPath)) {
        return res.status(404).json({ error: "Video not found" });
    }

    const tmpName = crypto.randomBytes(8).toString("hex") + ".jpg";
    const tmpPath = path.join("/tmp", tmpName);

    ffmpeg(videoPath)
        .screenshots({
            count: 1,
            timemarks: ["0"], // first frame
            filename: tmpName,
            folder: "/tmp" // temporary folder
        })
        // .output(tmpPath)
        .on("end", function() {
            res.sendFile(tmpPath, err => {
                if (err) res.status(500).json({ error: "Error generating thumbnail" });
            });
        })
        .on("error", function(err) {
            console.error(err);
            res.status(500).json({ error: "Error generating thumbnail" });
        });
};

export const processVideo = (req, res) => {
    const { filename } = req.params;
    const { targetColor, threshold } = req.query;

    if (!targetColor || !threshold) {
        return res.status(400).json({ error: "Missing targetColor or threshold query parameter." });
    }

    try {
        const job = processVideoJob(filename, targetColor, parseInt(threshold, 10));
        res.status(202).json(job);
    } catch (err) {
        console.error("processVideo error:", err);
        res.status(500).json({ error: "pV Error starting job", details: err.message });
    }
};

export const binarizerImg = (req, res) => {
    const { filename } = req.params;
    const { targetColor, threshold } = req.query;

    if (!filename) {
        return res.status(400).json({ error: "Missing filename parameter." });
    }

    if (!targetColor || !threshold) {
        return res.status(400).json({ error: "Missing targetColor or threshold query parameter." });
    }

    try {
        // const job = processVideoJob(filename, targetColor, parseInt(threshold, 10));
        res.status(202).json({ message: "binarizerImg endpoint hit", filename, targetColor, threshold });
    } catch (err) {
        console.error("processVideo error:", err);
        res.status(500).json({ error: "pV Error starting job", details: err.message });
    }
};

export const getStatus = (req, res) => {
    const { jobId } = req.params;
    const status = getJobStatus(jobId);

    if (!status) return res.status(404).json({ error: "Job ID not found" });

    res.status(200).json(status);
};
