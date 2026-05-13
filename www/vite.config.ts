import tailwindcss from '@tailwindcss/vite';
import { defineConfig } from 'vitest/config';
import { playwright } from '@vitest/browser-playwright';
import { sveltekit } from '@sveltejs/kit/vite';

export default defineConfig({
	plugins: [tailwindcss(), sveltekit()],

	resolve: {
		alias: [
			{
				find: '@atlaskit/pragmatic-drag-and-drop/element/adapter',
				replacement: '@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/element/adapter.js'
			},
			{
				find: '@atlaskit/pragmatic-drag-and-drop/combine',
				replacement: '@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/combine.js'
			},
			{
				find: '@atlaskit/pragmatic-drag-and-drop/reorder',
				replacement: '@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/reorder.js'
			},
			{
				find: '@atlaskit/pragmatic-drag-and-drop-hitbox/closest-edge',
				replacement: '@atlaskit/pragmatic-drag-and-drop-hitbox/dist/esm/closest-edge.js'
			},
			{
				find: '@atlaskit/pragmatic-drag-and-drop-hitbox/reorder-with-edge',
				replacement: '@atlaskit/pragmatic-drag-and-drop-hitbox/dist/esm/reorder-with-edge.js'
			}
		]
	},

	optimizeDeps: {
		include: [
			'@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/element/adapter.js',
			'@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/combine.js',
			'@atlaskit/pragmatic-drag-and-drop/dist/esm/entry-point/reorder.js',
			'@atlaskit/pragmatic-drag-and-drop-hitbox/dist/esm/closest-edge.js',
			'@atlaskit/pragmatic-drag-and-drop-hitbox/dist/esm/reorder-with-edge.js'
		]
	},

	ssr: {
		noExternal: [
			'svelte-sonner',
			'@atlaskit/pragmatic-drag-and-drop',
			'@atlaskit/pragmatic-drag-and-drop-hitbox'
		]
	},

	test: {
		expect: { requireAssertions: true },
		projects: [
			{
				extends: './vite.config.ts',
				test: {
					name: 'client',
					browser: {
						enabled: true,
						provider: playwright(),
						instances: [{ browser: 'chromium', headless: true }]
					},
					include: ['src/**/*.svelte.{test,spec}.{js,ts}'],
					exclude: ['src/lib/server/**']
				}
			},
			{
				extends: './vite.config.ts',
				test: {
					name: 'server',
					environment: 'node',
					include: ['src/**/*.{test,spec}.{js,ts}'],
					exclude: ['src/**/*.svelte.{test,spec}.{js,ts}']
				}
			}
		]
	}
});
