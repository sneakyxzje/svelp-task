<script lang="ts">
	import Button from '../ui/Button.svelte';
	import { Mountain } from 'lucide-svelte';
</script>

<header
	class="sticky top-0 z-50 w-full border-b border-border-subtle bg-marketing-black/80 backdrop-blur-md"
>
	<div class="container mx-auto flex h-16 items-center justify-between px-4 lg:px-8">
		<div class="flex items-center gap-2">
			<Mountain class="h-6 w-6 text-brand-indigo" />
			<span class="font-weight-strong text-xl tracking-[-0.704px]">Svelp</span>
		</div>

		<nav class="hidden items-center gap-8 md:flex">
			{#each ['Product', 'Features', 'Pricing', 'Changelog'] as link}
				<a
					href="/"
					class="font-weight-signature text-sm text-text-secondary transition-colors hover:text-text-primary"
				>
					{link}
				</a>
			{/each}
		</nav>

		<div class="flex items-center gap-4">
			<a
				href="/login"
				class="font-weight-signature text-sm text-text-secondary hover:text-text-primary"
			>
				Log in
			</a>
			<Button variant="primary" size="sm">Sign up</Button>
		</div>
	</div>
</header>
