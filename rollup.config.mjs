import { nodeResolve } from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';

const bundles = [
  {
    input:      'target/cljs/gallery/gallery.js',
    outputFile: 'resources/public/js/gallery.js'
  },
  {
    input:      'target/cljs/landing/landing.js',
    outputFile: 'resources/public/js/landing.js'
  }
];

export default bundles.map(({ input, outputFile }) => ({
  input,
  output: {
    file:      outputFile,
    format:    'iife',
    sourcemap: false
  },
  plugins: [
    nodeResolve({ browser: true }),
    terser()
  ]
}));
