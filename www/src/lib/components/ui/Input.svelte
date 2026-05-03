<script lang="ts">
	import { cn } from '$lib/utils/cn';
	import type { HTMLInputAttributes } from 'svelte/elements';

	interface Props extends HTMLInputAttributes {
		label?: string;
		error?: string;
		class?: string;
		value?: any;
	}

	let { label, error, class: className, value = $bindable(), ...rest }: Props = $props();
</script>

<div class="flex w-full flex-col gap-1.5">
	{#if label}
		<label class="font-weight-signature ml-1 text-xs text-text-tertiary" for={rest.id}>
			{label}
		</label>
	{/if}
	<input
		bind:value={value}
		class={cn(
			'flex h-10 w-full rounded-comfortable border border-border-standard bg-white/[0.02] px-3 py-2 text-sm text-text-primary ring-offset-background transition-all file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-text-quaternary focus-visible:border-accent-violet focus-visible:ring-1 focus-visible:ring-accent-violet focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50',
			error && 'border-destructive focus-visible:ring-destructive',
			className
		)}
		{...rest}
	/>
	{#if error}
		<p class="ml-1 text-[10px] text-destructive">{error}</p>
	{/if}
</div>
