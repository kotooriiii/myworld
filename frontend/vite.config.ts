import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import mkcert from "vite-plugin-mkcert";

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    https: true,
    host: true,
    port: 443,
    strictPort: true,
  },
  plugins: [react(), mkcert()],
})
