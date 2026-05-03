<script lang="ts">
	import { cn } from '$lib/utils/cn';
	import type { HTMLButtonAttributes } from 'svelte/elements';

	interface Props extends HTMLButtonAttributes {
		variant?: 'primary' | 'ghost' | 'subtle' | 'outline' | 'secondary';
		size?: 'sm' | 'md' | 'lg' | 'icon';
		class?: string;
	}

	let { variant = 'ghost', size = 'md', class: className, children, ...rest }: Props = $props();

	const variants = {
		primary:
			'bg-brand-indigo text-white hover:bg-accent-hover shadow-[0_0_20px_rgba(94,106,210,0.3)] border border-white/10',
		secondary:
			'bg-surface-level-3 text-text-primary border border-border-standard hover:bg-surface-secondary',
		ghost: 'bg-transparent text-text-secondary hover:text-text-primary hover:bg-white/[0.05]',
		subtle:
			'bg-white/[0.02] text-text-tertiary border border-border-subtle hover:bg-white/[0.05] hover:text-text-secondary',
		outline:
			'bg-transparent border border-border-standard text-text-primary hover:bg-white/[0.05] hover:border-border-tertiary'
	};

	const sizes = {
		sm: 'h-8 px-3 text-[13px]',
		md: 'h-10 px-4 text-sm',
		lg: 'h-12 px-6 text-base',
		icon: 'h-10 w-10 p-0 rounded-full'
	};
</script>

<button
	class={cn(
		'font-weight-signature inline-flex items-center justify-center rounded-comfortable transition-all duration-200 outline-none focus-visible:ring-2 focus-visible:ring-accent-violet/50 active:scale-[0.97] disabled:pointer-events-none disabled:opacity-50',
		variants[variant],
		sizes[size],
		className
	)}
	{...rest}
>
	{@render children?.()}
</button>
