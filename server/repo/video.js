import fs from "fs";
import path from 'path';
import { fileURLToPath } from "url";
import dotenv from "dotenv"

dotenv.config();

const { VIDEOS_DIR } = process.env;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const sampleInputPath = path.join(__dirname, VIDEOS_DIR);

try {
    const files = fs.readdirSync(sampleInputPath);

    const mp4Files = files.filter(file => {
        const filePath = path.join(sampleInputPath, file);
        return fs.statSync(filePath).isFile() && path.extname(file).toLowerCase() === '.mp4';
    });

    console.log(mp4Files);
} catch (err) {
    console.error('Error reading folder:', err);
}

export default mp4Files;