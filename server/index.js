//Daniel - AI generate

import express from "express";
import dotenv from "dotenv";
import path from "path";

import apiRoutes from "./routes/api.js";
import thumbnailRoutes from "./routes/thumbnail.js";
import processRoutes from "./routes/process.js";

// Optional: try loading .env if it exists (won’t break if missing)
dotenv.config({ path: path.resolve("../.env") });

const app = express();
const PORT = process.env.PORT || 3000;

// Provide fallback paths that match the Docker volume mounts
const videoDir = process.env.VIDEO_DIR || "/videos";
const resultsDir = process.env.RESULTS_DIR || "/results";

// Serve static directories
app.use("/videos", express.static(videoDir));
app.use("/results", express.static(resultsDir));

// Mount API routes
app.use("/api", apiRoutes);
app.use("/thumbnail", thumbnailRoutes);
app.use("/process", processRoutes);

// Health check
app.get("/", (req, res) => {
  res.send("Salamander Video Processing Server is running!");
});

app.listen(PORT, () => console.log(`Server running at http://localhost:${PORT}`));