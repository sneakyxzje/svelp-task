<script lang="ts">
	import './layout.css';
	let { children } = $props();
</script>

<div class="relative min-h-screen bg-marketing-black text-text-primary selection:bg-brand-indigo/30 selection:text-white">
	<!-- Global Grainy Overlay (Optional, for that premium feel) -->
	<div class="fixed inset-0 pointer-events-none z-50 opacity-[0.03] mix-blend-overlay bg-[url('https://grainy-gradients.vercel.app/noise.svg')]"></div>
	
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
