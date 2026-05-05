<script lang="ts">
	import { Plus, LayoutGrid, Star, Clock, MoreHorizontal, Layout } from 'lucide-svelte';
	import { fly, fade } from 'svelte/transition';
	import Button from '$lib/components/ui/Button.svelte';
	import Input from '$lib/components/ui/Input.svelte';
	import { toast } from 'svelte-sonner';
	import { http } from '$lib/http/http';

	let { data } = $props();
	let workspace = $derived(data.workspace);
	let boards = $derived(data.boards);

	let showCreateModal = $state(false);
	let newBoardName = $state('');
	let newBoardSlug = $state('');
	let isSubmitting = $state(false);

	$effect(() => {
		if (newBoardName) {
			newBoardSlug = newBoardName
				.toLowerCase()
				.normalize('NFD')
				.replace(/[\u0300-\u036f]/g, '')
				.replace(/[đĐ]/g, 'd')
				.replace(/([^0-9a-z-\s])/g, '')
				.replace(/(\s+)/g, '-')
				.replace(/-+/g, '-')
				.replace(/^-+|-+$/g, '');
		}
	});

	async function handleCreateBoard() {
		if (!newBoardName) return;
		isSubmitting = true;

		const res = await http.post('/boards', {
			name: newBoardName,
			workspaceId: workspace?.id,
			slug: newBoardSlug,
			description: ''
		});

		if (res.data) {
			toast.success('Board created successfully');
			showCreateModal = false;
			newBoardName = '';
			window.location.reload();
		} else {
			toast.error(res.error || 'Failed to create board');
		}
		isSubmitting = false;
	}
</script>

<div class="w-full" in:fade={{ duration: 200 }}>
	<div class="mb-10 flex items-center justify-between">
		<div>
			<h1 class="text-3xl font-bold tracking-tight text-text-primary">Boards</h1>
			<p class="mt-2 text-text-tertiary">
				Manage and organize your project boards for this workspace.
			</p>
		</div>
		<Button variant="primary" onclick={() => (showCreateModal = true)}>
			<Plus size={18} class="mr-2" />
			New Board
		</Button>
	</div>

	{#if boards.length === 0}
		<div
			class="flex flex-col items-center justify-center rounded-xl border border-white/[0.05] bg-white/[0.02] py-20 text-center"
			in:fly={{ y: 10, duration: 400 }}
		>
			<div
				class="mb-4 flex h-16 w-16 items-center justify-center rounded-full bg-brand-indigo/10 text-brand-indigo"
			>
				<Layout size={32} />
			</div>
			<h2 class="text-lg font-medium text-text-primary">No boards found</h2>
			<p class="mx-auto mt-2 max-w-xs text-sm text-text-tertiary">
				Create your first project board to start tracking tasks and collaborating with your team.
			</p>
			<Button variant="outline" class="mt-6" onclick={() => (showCreateModal = true)}>
				<Plus size={16} class="mr-2" />
				Create your first board
			</Button>
		</div>
	{:else}
		<div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
			{#each boards as board}
				<a
					href="/w/{workspace?.slug}/b/{board.slug}"
					class="group relative flex flex-col justify-between overflow-hidden rounded-xl border border-white/[0.05] bg-white/[0.02] p-5 transition-all hover:border-white/[0.1] hover:bg-white/[0.04]"
					in:fly={{ y: 10, duration: 300 }}
				>
					<div class="mb-4 flex items-center justify-between">
						<div
							class="flex h-10 w-10 items-center justify-center rounded-lg bg-white/[0.05] text-text-secondary transition-colors group-hover:text-brand-indigo"
						>
							<LayoutGrid size={20} />
						</div>
						<button
							class="text-text-tertiary opacity-0 transition-opacity group-hover:opacity-100 hover:text-text-primary"
						>
							<Star size={16} />
						</button>
					</div>

					<div>
						<h3 class="font-medium text-text-primary">{board.name}</h3>
						<p class="mt-1 line-clamp-2 text-xs leading-relaxed text-text-tertiary">
							{board.description || 'No description provided for this board.'}
						</p>
					</div>

					<div class="mt-6 flex items-center justify-between border-t border-white/[0.03] pt-4">
						<div
							class="flex items-center gap-2 text-[10px] tracking-wider text-text-tertiary uppercase"
						>
							<Clock size={12} />
							<span>Updated recently</span>
						</div>
						<div class="flex -space-x-2">
							<div
								class="flex h-6 w-6 items-center justify-center rounded-full border border-[#08090a] bg-brand-indigo text-[10px] font-bold"
							>
								U
							</div>
						</div>
					</div>
				</a>
			{/each}

			<button
				class="flex h-full min-h-[180px] flex-col items-center justify-center rounded-xl border border-dashed border-white/[0.1] bg-transparent text-text-tertiary transition-all hover:border-brand-indigo/50 hover:bg-brand-indigo/5 hover:text-brand-indigo"
				onclick={() => (showCreateModal = true)}
			>
				<Plus size={24} class="mb-2" />
				<span class="text-sm font-medium">Add new board</span>
			</button>
		</div>
	{/if}
</div>

{#if showCreateModal}
	<div
		class="fixed inset-0 z-50 flex items-center justify-center p-4"
		transition:fade={{ duration: 150 }}
		role="button"
		tabindex="-1"
		onclick={() => (showCreateModal = false)}
		onkeydown={(e) => e.key === 'Escape' && (showCreateModal = false)}
	>
		<div class="absolute inset-0 bg-black/60 backdrop-blur-sm"></div>

		<div
			class="relative w-full max-w-md overflow-hidden rounded-2xl border border-white/[0.1] bg-[#0c0d0e] shadow-2xl"
			in:fly={{ y: 20, duration: 300, delay: 50 }}
			role="button"
			tabindex="0"
			onclick={(e) => e.stopPropagation()}
			onkeydown={(e) => e.stopPropagation()}
		>
			<div class="p-6">
				<h2 class="text-xl font-semibold text-text-primary">Create new board</h2>
				<p class="mt-1 text-sm text-text-tertiary">Start a new project or workstream.</p>

				<div class="mt-8 space-y-5">
					<div class="space-y-2">
						<label
							for="name"
							class="text-xs font-medium tracking-wider text-text-tertiary uppercase"
							>Board Name</label
						>
						<Input
							id="name"
							bind:value={newBoardName}
							placeholder="e.g. Product Roadmap"
							autofocus
						/>
					</div>

					<div class="space-y-2">
						<label
							for="slug"
							class="text-xs font-medium tracking-wider text-text-tertiary uppercase"
							>URL Slug</label
						>
						<div
							class="flex items-center rounded-lg border border-white/[0.05] bg-white/[0.02] px-3"
						>
							<span class="text-xs text-text-quaternary select-none">/b/</span>
							<input
								id="slug"
								bind:value={newBoardSlug}
								class="w-full bg-transparent py-2.5 text-sm text-brand-indigo outline-none"
							/>
						</div>
					</div>
				</div>

				<div class="mt-10 flex justify-end gap-3">
					<Button variant="ghost" onclick={() => (showCreateModal = false)}>Cancel</Button>
					<Button variant="primary" onclick={handleCreateBoard} loading={isSubmitting}>
						Create Board
					</Button>
				</div>
			</div>
		</div>
	</div>
{/if}

<style>
</style>
