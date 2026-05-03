<script lang="ts">
	import { cn } from '$lib/utils/cn';
	import {
		Mountain,
		Search,
		Inbox,
		Clock,
		Settings,
		Plus,
		ChevronDown,
		LayoutGrid,
		Layers,
		CheckCircle2,
		Users,
		Star
	} from 'lucide-svelte';

	import { page } from '$app/state';
	import { fly } from 'svelte/transition';

	interface Props {
		workspaces?: any[];
		activeWorkspace?: any;
	}

	let { workspaces = [], activeWorkspace }: Props = $props();

	const isWorkspaceMode = $derived(page.url.pathname.startsWith('/w/'));

	const navItems = [
		{ icon: LayoutGrid, label: 'Dashboard', href: '/dashboard', active: !isWorkspaceMode },
		{ icon: Search, label: 'Search', shortcut: '⌘K' },
		{ icon: Inbox, label: 'Notifications', badge: 3 }
	];

	const workspaceNav = [
		{ icon: CheckCircle2, label: 'My Tasks' },
		{ icon: Layers, label: 'All Projects' },
		{ icon: Users, label: 'Team Members' }
	];
</script>

<aside
	class="flex h-screen w-64 flex-col border-r border-white/[0.05] bg-[#08090a] text-text-secondary select-none"
>
	<div class="mb-2 p-4">
		<a href="/dashboard" class="group flex items-center justify-between">
			<div class="flex items-center gap-2.5 px-2">
				<div
					class="flex h-6 w-6 items-center justify-center rounded-md bg-gradient-to-br from-brand-indigo to-accent-violet shadow-lg shadow-brand-indigo/20 transition-transform group-hover:scale-110"
				>
					<Mountain class="h-3.5 w-3.5 text-white" />
				</div>
				<span class="font-weight-strong text-base tracking-[-0.03em] text-text-primary">
					Svelp
				</span>
			</div>
		</a>
	</div>

	<nav class="scrollbar-none flex-1 overflow-y-auto px-2 py-2">
		<div class="space-y-0.5">
			{#each navItems as item}
				<a
					href={item.href || '#'}
					class={cn(
						'group flex w-full items-center justify-between rounded-md px-2.5 py-1.5 text-sm transition-colors hover:bg-white/[0.05] hover:text-text-primary',
						item.active ? 'bg-white/[0.05] text-text-primary' : 'text-text-tertiary'
					)}
				>
					<div class="flex items-center gap-2.5">
						<item.icon class="h-4 w-4 transition-colors group-hover:text-text-secondary" />
						<span>{item.label}</span>
					</div>
					{#if item.shortcut}
						<span
							class="font-mono text-[10px] text-text-quaternary opacity-0 transition-opacity group-hover:opacity-100"
							>{item.shortcut}</span
						>
					{/if}
					{#if item.badge}
						<span
							class="flex h-4 min-w-4 items-center justify-center rounded-full bg-brand-indigo/20 px-1 text-[10px] font-bold text-brand-indigo"
							>{item.badge}</span
						>
					{/if}
				</a>
			{/each}
		</div>

		{#if isWorkspaceMode}
			<div class="mt-8 px-2.5" in:fly={{ y: 10, duration: 300 }}>
				<div class="mb-2 flex items-center justify-between">
					<h3 class="text-[11px] font-bold tracking-wider text-text-quaternary uppercase">
						{activeWorkspace?.name || 'Workspace'}
					</h3>
					<button
						class="rounded-md p-1 text-text-quaternary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
					>
						<Settings class="h-3.5 w-3.5" />
					</button>
				</div>
				<div class="space-y-0.5">
					{#each workspaceNav as item}
						<button
							class="group flex w-full items-center gap-2.5 rounded-md px-2.5 py-1.5 text-sm text-text-tertiary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
						>
							<item.icon class="h-4 w-4 transition-colors group-hover:text-text-secondary" />
							<span>{item.label}</span>
						</button>
					{/each}
				</div>
			</div>

			<div class="mt-8 px-2.5">
				<div class="mb-2 flex items-center justify-between">
					<h3 class="text-[11px] font-bold tracking-wider text-text-quaternary uppercase">
						Favorite Projects
					</h3>
					<a
						href="/workspaces/new"
						class="rounded-md p-1 text-text-quaternary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
					>
						<Plus class="h-3.5 w-3.5" />
					</a>
				</div>
				<div class="space-y-0.5">
					{#each workspaces as ws}
						<a
							href="/w/{ws.slug}"
							class="group flex w-full items-center gap-2.5 rounded-md px-2.5 py-1.5 text-sm text-text-tertiary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
						>
							<div class="h-2 w-2 rounded-full bg-accent-violet"></div>
							<span class="truncate">{ws.name}</span>
						</a>
					{/each}
				</div>
			</div>
		{:else}
			<div class="mt-8 px-2.5" in:fly={{ y: 10, duration: 300 }}>
				<div class="mb-2">
					<h3 class="text-[11px] font-bold tracking-wider text-text-quaternary uppercase">
						Quick Access
					</h3>
				</div>
				<div class="space-y-0.5">
					<a
						href="/workspaces/new"
						class="group flex w-full items-center gap-2.5 rounded-md px-2.5 py-1.5 text-sm text-text-tertiary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
					>
						<Plus class="h-4 w-4" />
						<span>New Workspace</span>
					</a>
					<button
						class="group flex w-full items-center gap-2.5 rounded-md px-2.5 py-1.5 text-sm text-text-tertiary transition-colors hover:bg-white/[0.05] hover:text-text-primary"
					>
						<Star class="h-4 w-4" />
						<span>Starred</span>
					</button>
				</div>
			</div>
		{/if}
	</nav>

	<div class="border-t border-white/[0.05] p-4">
		<button
			class="group flex w-full items-center gap-3 rounded-lg p-1 transition-all hover:bg-white/[0.05]"
		>
			<div
				class="flex h-8 w-8 items-center justify-center rounded-full border border-white/[0.1] bg-gradient-to-t from-white/[0.05] to-white/[0.15] text-[11px] font-bold text-text-primary"
			>
				JD
			</div>
			<div class="flex flex-1 flex-col items-start overflow-hidden text-left">
				<span class="w-full truncate text-xs font-medium text-text-primary">John Doe</span>
				<span
					class="w-full truncate text-[10px] text-text-tertiary group-hover:text-text-quaternary"
					>Free Plan</span
				>
			</div>
			<Settings
				class="h-4 w-4 text-text-quaternary transition-colors group-hover:text-text-secondary"
			/>
		</button>
	</div>
</aside>

<style>
	.scrollbar-none::-webkit-scrollbar {
		display: none;
	}
</style>
