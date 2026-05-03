<script lang="ts">
	import Button from '$lib/components/ui/Button.svelte';
	import Input from '$lib/components/ui/Input.svelte';
	import { http } from '$lib/http/http';
	import { Mountain, ArrowRight } from 'lucide-svelte';

	let email = $state('');
	let password = $state('');
	let isLoading = $state(false);

	const handleSubmit = async (e: Event) => {
		e.preventDefault();
		isLoading = true;
		const { data, error } = await http.post('/auth/login', {
			identity: email,
			password: password
		});
		if (error) {
			console.error('Thất bại: ' + error);
		} else {
			console.log('Đăng nhập thành công!');
		}
		isLoading = false;
	};
</script>

<svelte:head>
	<title>Log in — Svelp</title>
</svelte:head>

<div class="relative flex min-h-screen w-full items-center justify-center overflow-hidden">
	<!-- Background Ambient Glows -->
	<div
		class="pointer-events-none absolute -top-[10%] left-1/2 h-[600px] w-full -translate-x-1/2 rounded-full bg-brand-indigo/10 blur-[150px]"
	></div>
	<div
		class="pointer-events-none absolute right-0 bottom-0 h-[400px] w-[400px] rounded-full bg-accent-violet/5 blur-[120px]"
	></div>

	<div class="relative z-10 w-full max-w-[420px] px-6">
		<!-- Brand Logo -->
		<div class="mb-10 flex justify-center">
			<div
				class="flex h-12 w-12 items-center justify-center rounded-2xl border border-white/[0.08] bg-white/[0.03] shadow-2xl"
			>
				<Mountain class="h-6 w-6 text-text-primary" />
			</div>
		</div>

		<!-- Main Card -->
		<div
			class="rounded-panel border border-white/[0.08] bg-[#0f1011]/50 p-8 shadow-[rgba(0,0,0,0.4)_0px_30px_60px_-12px,rgba(255,255,255,0.02)_0px_0px_0px_1px_inset] backdrop-blur-2xl"
		>
			<div class="mb-8">
				<h1 class="font-weight-strong mb-2 text-2xl tracking-[-0.03em] text-text-primary">
					Log in to Svelp
				</h1>
				<p class="text-[13px] text-text-tertiary">Welcome back! Please enter your details.</p>
			</div>

			<form onsubmit={handleSubmit} class="space-y-6">
				<Input
					label="Email or Username"
					placeholder="name@company.com"
					type="text"
					required
					bind:value={email}
				/>

				<div class="space-y-1.5">
					<div class="flex items-center justify-between px-1">
						<label class="font-weight-signature text-[12px] text-text-tertiary" for="password"
							>Password</label
						>
						<a
							href="/forgot-password"
							class="text-[11px] text-accent-violet transition-colors hover:text-accent-hover"
							>Forgot?</a
						>
					</div>
					<Input
						id="password"
						placeholder="••••••••"
						type="password"
						required
						bind:value={password}
					/>
				</div>

				<Button type="submit" variant="primary" class="group h-11 w-full" disabled={isLoading}>
					{isLoading ? 'Signing in...' : 'Sign in to Svelp'}
					{#if !isLoading}
						<ArrowRight class="ml-2 h-4 w-4 transition-transform group-hover:translate-x-1" />
					{/if}
				</Button>
			</form>

			<div class="relative my-8">
				<div class="absolute inset-0 flex items-center">
					<span class="w-full border-t border-white/[0.05]"></span>
				</div>
				<div class="relative flex justify-center text-[11px] tracking-wider uppercase">
					<span class="bg-[#0f1011]/80 px-4 text-text-quaternary backdrop-blur-sm">Or</span>
				</div>
			</div>

			<Button variant="outline" class="h-11 w-full border-white/[0.05] hover:bg-white/[0.03]">
				Continue with GitHub
			</Button>
		</div>

		<div class="mt-8 flex flex-col items-center gap-4">
			<p class="text-[13px] text-text-tertiary">
				Don't have an account?
				<a
					href="/register"
					class="font-weight-signature text-text-primary decoration-white/20 underline-offset-4 transition-all hover:underline"
					>Sign up</a
				>
			</p>

			<div class="flex gap-4 opacity-50 transition-opacity hover:opacity-100">
				<a href="/" class="text-[11px] text-text-quaternary hover:text-text-primary"
					>Privacy Policy</a
				>
				<a href="/" class="text-[11px] text-text-quaternary hover:text-text-primary"
					>Terms of Service</a
				>
			</div>
		</div>
	</div>
</div>
