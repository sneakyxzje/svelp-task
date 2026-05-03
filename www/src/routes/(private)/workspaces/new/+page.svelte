<script lang="ts">
	import { Mountain, Info, Globe, ArrowRight, Loader2 } from 'lucide-svelte';
	import Input from '$lib/components/ui/Input.svelte';
	import Button from '$lib/components/ui/Button.svelte';
	import { fly } from 'svelte/transition';
	import { workspaceService } from '$lib/services/workspace.service';
	import { goto } from '$app/navigation';
	import { toast } from 'svelte-sonner';

	let name = $state('');
	let description = $state('');
	let slug = $state('');
	let isLoading = $state(false);

	$effect(() => {
		if (name) {
			slug = name
				.toLowerCase()
				.normalize('NFD')
				.replace(/[\u0300-\u036f]/g, '')
				.replace(/đ/g, 'd')
				.replace(/[^a-z0-9\s-]/g, '')
				.replace(/\s+/g, '-')
				.replace(/-+/g, '-')
				.replace(/^-|-$/g, '');
		}
	});

	async function handleSubmit(e: Event) {
		e.preventDefault();
		isLoading = true;
		const { data, error } = await workspaceService.create({ name, description, slug });
		if (error) {
			console.error(error);
		} else {
			toast.success('Workspace created successfully!');
			goto('/dashboard');
		}
		setTimeout(() => {
			isLoading = false;
		}, 1000);
	}
</script>

<svelte:head>
	<title>Create Workspace</title>
</svelte:head>

<div class="flex flex-col items-center justify-center py-4" in:fly={{ y: 10, duration: 400 }}>
	<div class="w-full max-w-xl space-y-8">
		<div class="flex flex-col items-center text-center">
			<div
				class="mb-4 flex h-12 w-12 items-center justify-center rounded-2xl border border-white/[0.08] bg-white/[0.03] shadow-2xl"
			>
				<Mountain class="h-6 w-6 text-brand-indigo" />
			</div>
			<h1 class="font-weight-strong text-2xl tracking-[-0.03em] text-text-primary">
				Create a new workspace
			</h1>
			<p class="mt-1 text-sm text-text-tertiary">Set up your team's new base of operations.</p>
		</div>

		<form
			onsubmit={handleSubmit}
			class="rounded-panel border border-white/[0.08] bg-[#0f1011]/50 p-8 shadow-2xl backdrop-blur-xl"
		>
			<div class="space-y-6">
				<div class="space-y-2">
					<Input
						label="Workspace Name"
						placeholder="Acme Corp, Design Team..."
						bind:value={name}
						required
						autofocus
					/>
					<p class="flex items-center gap-1.5 px-1 text-[11px] text-text-quaternary">
						<Info class="h-3 w-3" />
						The display name of your workspace.
					</p>
				</div>

				<div class="space-y-2">
					<div class="relative">
						<Input
							label="Workspace URL"
							placeholder="your-workspace-slug"
							bind:value={slug}
							required
						/>
						<div
							class="absolute right-3 bottom-2.5 flex items-center gap-1.5 text-[11px] text-text-quaternary"
						>
							<Globe class="h-3 w-3" />
							<span>svelp.com/</span>
						</div>
					</div>
					<p class="px-1 text-[11px] text-text-quaternary">
						Must be unique and contain only letters, numbers, and dashes.
					</p>
				</div>

				<div class="space-y-1.5">
					<label class="font-weight-signature px-1 text-[12px] text-text-tertiary" for="desc"
						>Description (Optional)</label
					>
					<textarea
						id="desc"
						bind:value={description}
						placeholder="Briefly describe the purpose of this workspace..."
						class="min-h-[80px] w-full rounded-md border border-white/[0.08] bg-white/[0.03] p-3 text-sm text-text-primary transition-all placeholder:text-text-quaternary focus:border-brand-indigo/50 focus:ring-1 focus:ring-brand-indigo/50 focus:outline-none"
					></textarea>
				</div>
			</div>

			<div class="mt-8 flex items-center justify-end gap-4 border-t border-white/[0.05] pt-6">
				<a
					href="/dashboard"
					class="text-sm text-text-tertiary transition-colors hover:text-text-primary"
				>
					Cancel
				</a>
				<Button type="submit" variant="primary" class="h-10 gap-2 px-6" disabled={isLoading}>
					{#if isLoading}
						<Loader2 class="h-4 w-4 animate-spin" />
						Creating...
					{:else}
						Create Workspace
						<ArrowRight class="h-4 w-4" />
					{/if}
				</Button>
			</div>
		</form>
	</div>
</div>
