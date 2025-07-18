import { nodeResolve } from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';

export default {
  input:  'target/cljs/gallery_to-bundle.js',
  output: {
    file:   'resources/public/js/gallery.js',
    format: 'iife',
    sourcemap: false
  },
  plugins: [
    nodeResolve({ browser: true }),
    terser()
  ]
};
