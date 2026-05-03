<script lang="ts">
	import { Mountain, Plus, Users, ArrowRight, LayoutGrid, Settings, Star } from 'lucide-svelte';
	import { fly } from 'svelte/transition';
	let { data } = $props();
	let workspaces = $derived(data.workspaces || []);
</script>

<svelte:head>
	<title>Svelp Dashboard</title>
</svelte:head>

<div class="space-y-12 py-6" in:fly={{ y: 20, duration: 600 }}>
	<div class="flex flex-col items-center space-y-2 text-center">
		<h1 class="font-weight-strong text-3xl tracking-[-0.03em] text-text-primary">Welcome back</h1>
		<p class="text-text-tertiary">Select a workspace to start collaborating with your team.</p>
	</div>

	<div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
		{#each workspaces as ws, i}
			<a
				href="/w/{ws.slug}"
				class="group relative flex flex-col justify-between overflow-hidden rounded-panel border border-white/[0.05] bg-white/[0.02] p-6 transition-all hover:border-white/[0.1] hover:bg-white/[0.04] hover:shadow-[0_0_40px_-10px_rgba(94,106,210,0.15)]"
				in:fly={{ y: 20, duration: 400, delay: i * 100 }}
			>
				<div
					class="pointer-events-none absolute -top-8 -right-8 h-24 w-24 rounded-full bg-brand-indigo/5 blur-2xl transition-all group-hover:bg-brand-indigo/10"
				></div>

				<div class="space-y-4">
					<div class="flex items-start justify-between">
						<div
							class="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br shadow-lg shadow-black/20"
						>
							<Mountain class="h-6 w-6 text-white" />
						</div>
						<button
							class="rounded-md p-1.5 text-text-quaternary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
						>
							<Star class="h-4 w-4" />
						</button>
					</div>

					<div>
						<h3
							class="font-weight-strong text-lg text-text-primary transition-colors group-hover:text-brand-indigo"
						>
							{ws.name}
						</h3>
						<p class="text-sm text-text-tertiary">svelp.com/{ws.slug}</p>
					</div>
				</div>

				<div class="mt-8 flex items-center justify-between border-t border-white/[0.05] pt-4">
					<div class="flex items-center gap-1.5 text-xs text-text-tertiary">
						<Users class="h-3.5 w-3.5" />
						<!-- <span>{ws.members} members</span> -->
					</div>
					<div
						class="flex h-8 w-8 items-center justify-center rounded-full bg-white/[0.03] text-text-quaternary transition-all group-hover:bg-brand-indigo group-hover:text-white"
					>
						<ArrowRight class="h-4 w-4" />
					</div>
				</div>
			</a>
		{/each}

		<a
			href="/workspaces/new"
			class="group flex flex-col items-center justify-center space-y-3 rounded-panel border border-dashed border-white/10 bg-transparent p-6 transition-all hover:border-brand-indigo/50 hover:bg-brand-indigo/[0.02]"
			in:fly={{ y: 20, duration: 400, delay: workspaces.length * 100 }}
		>
			<div
				class="flex h-12 w-12 items-center justify-center rounded-full bg-white/[0.03] text-text-quaternary transition-all group-hover:bg-brand-indigo/10 group-hover:text-brand-indigo"
			>
				<Plus class="h-6 w-6" />
			</div>
			<div class="text-center">
				<p class="font-weight-strong text-sm text-text-secondary group-hover:text-text-primary">
					Create Workspace
				</p>
				<p class="text-[11px] text-text-quaternary">Add a new team to Svelp</p>
			</div>
		</a>
	</div>

	<div class="mt-12 flex justify-center gap-8 border-t border-white/[0.05] pt-8">
		<a
			href="/"
			class="flex items-center gap-2 text-xs text-text-quaternary transition-colors hover:text-text-primary"
		>
			<LayoutGrid class="h-3.5 w-3.5" />
			Resources
		</a>
		<a
			href="/"
			class="flex items-center gap-2 text-xs text-text-quaternary transition-colors hover:text-text-primary"
		>
			<Settings class="h-3.5 w-3.5" />
			Account Settings
		</a>
	</div>
</div>

<style>
	:global(body) {
		background-image: radial-gradient(circle at 50% -20%, rgba(94, 106, 210, 0.1), transparent 40%);
	}
</style>
