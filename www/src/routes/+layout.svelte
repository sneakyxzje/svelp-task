<script lang="ts">
	import { Toaster } from 'svelte-sonner';
	import './layout.css';
	let { children } = $props();
</script>

<Toaster richColors theme="dark" position="bottom-right" />

<div
	class="relative min-h-screen bg-marketing-black text-text-primary selection:bg-brand-indigo/30 selection:text-white"
>
	<!-- Global Grainy Overlay (Optional, for that premium feel) -->
	<div
		class="pointer-events-none fixed inset-0 z-50 bg-[url('https://grainy-gradients.vercel.app/noise.svg')] opacity-[0.03] mix-blend-overlay"
	></div>

	{@render children()}
</div>

<style>
	:global(html) {
		background-color: #08090a;
		scroll-behavior: smooth;
	}

	:global(body) {
		margin: 0;
		padding: 0;
		overflow-x: hidden;
	}
</style>
