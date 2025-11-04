import { Router } from "express";
import { getVideos } from "../controllers/api.controller.js";

const router = Router();

router.get("/api", getVideos);

export default router;