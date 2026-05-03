<script lang="ts">
	import Sidebar from '$lib/components/layout/Sidebar.svelte';

	let { data, children } = $props();

	let workspaces = $state(data?.workspaces || []);
	let activeWorkspace = $derived(workspaces[0]);
</script>

<div class="flex h-screen w-full overflow-hidden bg-[#08090a]">
	<Sidebar {workspaces} {activeWorkspace} />

	<main class="relative flex flex-1 flex-col overflow-hidden">
		<div
			class="pointer-events-none absolute top-0 left-0 h-64 w-full bg-gradient-to-b from-brand-indigo/5 to-transparent"
		></div>

		<header class="flex h-12 shrink-0 items-center border-b border-white/[0.05] px-6">
			<div class="flex items-center gap-2 text-[13px]">
				<span class="text-text-tertiary">Workspaces</span>
				<span class="text-text-quaternary">/</span>
				<span class="font-medium text-text-primary">{activeWorkspace?.name || 'Home'}</span>
			</div>
		</header>

		<div class="flex-1 overflow-y-auto scroll-smooth p-8">
			<div class="mx-auto max-w-5xl">
				{@render children()}
			</div>
		</div>
	</main>
</div>

<style>
	:global(body) {
		background-color: #08090a;
		color: #e0e0e0;
	}
</style>
