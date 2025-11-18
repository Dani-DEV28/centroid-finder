import express from "express";
import { getVideos } from "../controllers/api.controller.js";

//Dani - Generate AI
import fs from "fs";

const router = express.Router();
router.get("/api", getVideos);

const videoDir = process.env.VIDEO_DIR;

// GET /api/videos
router.get("/videos", (req, res) => {
    fs.readdir(videoDir, (err, files) => {
        if (err) {
            return res.status(500).json({ error: "Error reading video directory" });
        }
        // Filter only video files
        const videoFiles = files.filter(file => /\.(mp4|mov|avi)$/i.test(file));
        res.json(videoFiles);
    });
});


export default router;