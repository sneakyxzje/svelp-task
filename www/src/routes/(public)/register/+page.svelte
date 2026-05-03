<script lang="ts">
	import Button from '$lib/components/ui/Button.svelte';
	import Input from '$lib/components/ui/Input.svelte';
	import { Mountain, ArrowRight, UserPlus } from 'lucide-svelte';

	let name = $state('');
	let email = $state('');
	let password = $state('');
	let confirmPassword = $state('');
	let isLoading = $state(false);

	function handleSubmit(e: Event) {
		e.preventDefault();
		isLoading = true;
	}
</script>

<svelte:head>
	<title>Create your account — Svelp</title>
</svelte:head>

<div class="relative flex min-h-screen w-full items-center justify-center overflow-hidden py-12">
	<div
		class="pointer-events-none absolute -top-[10%] right-1/2 h-[600px] w-full translate-x-1/2 rounded-full bg-brand-indigo/10 blur-[150px]"
	></div>
	<div
		class="pointer-events-none absolute bottom-0 left-0 h-[400px] w-[400px] rounded-full bg-accent-violet/5 blur-[120px]"
	></div>

	<div class="relative z-10 w-full max-w-[420px] px-6">
		<div class="mb-8 flex justify-center">
			<div
				class="flex h-12 w-12 items-center justify-center rounded-2xl border border-white/[0.08] bg-white/[0.03] shadow-2xl"
			>
				<Mountain class="h-6 w-6 text-text-primary" />
			</div>
		</div>

		<div
			class="rounded-panel border border-white/[0.08] bg-[#0f1011]/50 p-8 shadow-[rgba(0,0,0,0.4)_0px_30px_60px_-12px,rgba(255,255,255,0.02)_0px_0px_0px_1px_inset] backdrop-blur-2xl"
		>
			<div class="mb-6">
				<h1 class="font-weight-strong mb-2 text-2xl tracking-[-0.03em] text-text-primary">
					Create account
				</h1>
				<p class="text-[13px] text-text-tertiary">Join Svelp to start managing your projects.</p>
			</div>

			<form onsubmit={handleSubmit} class="space-y-4">
				<Input label="Full Name" placeholder="John Doe" type="text" required bind:value={name} />

				<Input
					label="Email Address"
					placeholder="name@company.com"
					type="email"
					required
					bind:value={email}
				/>

				<div class="grid grid-cols-2 gap-4">
					<Input
						label="Password"
						placeholder="••••••••"
						type="password"
						required
						bind:value={password}
					/>
					<Input
						label="Confirm"
						placeholder="••••••••"
						type="password"
						required
						bind:value={confirmPassword}
					/>
				</div>

				<Button type="submit" variant="primary" class="group mt-2 h-11 w-full" disabled={isLoading}>
					{isLoading ? 'Creating account...' : 'Create account'}
					{#if !isLoading}
						<UserPlus class="ml-2 h-4 w-4 transition-transform group-hover:scale-110" />
					{/if}
				</Button>
			</form>

			<div class="relative my-6">
				<div class="absolute inset-0 flex items-center">
					<span class="w-full border-t border-white/[0.05]"></span>
				</div>
				<div class="relative flex justify-center text-[11px] tracking-wider uppercase">
					<span class="bg-[#0f1011]/80 px-4 text-text-quaternary backdrop-blur-sm">Or</span>
				</div>
			</div>

			<Button variant="outline" class="h-11 w-full border-white/[0.05] hover:bg-white/[0.03]">
				Sign up with GitHub
			</Button>
		</div>

		<div class="mt-8 flex flex-col items-center gap-4">
			<p class="text-[13px] text-text-tertiary">
				Already have an account?
				<a
					href="/login"
					class="font-weight-signature text-text-primary decoration-white/20 underline-offset-4 transition-all hover:underline"
					>Log in</a
				>
			</p>
		</div>
	</div>
</div>

<style>
	:global(body) {
		background-color: #08090a;
	}
</style>
