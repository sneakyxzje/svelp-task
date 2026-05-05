<script lang="ts">
	import {
		X,
		User,
		ChevronDown,
		SquareCheck,
		Zap,
		Bug,
		Ellipsis,
		Paperclip,
		ChevronRight,
		Eye,
		Share2,
		Plus
	} from 'lucide-svelte';
	import { fade, scale, slide } from 'svelte/transition';
	import Button from '$lib/components/ui/Button.svelte';
	import type { CreateTaskRequest } from '$lib/services/task.service';

	let {
		show = $bindable(),
		columnId,
		onSave
	} = $props<{
		show: boolean;
		columnId: number | null;
		onSave: (data: CreateTaskRequest) => Promise<void>;
	}>();

	// Form State
	let title = $state('');
	let description = $state('');
	let priority = $state('Medium');
	let issueType = $state('Task');
	let isSubmitting = $state(false);

	// UI State
	let isDetailsOpen = $state(true);

	const issueTypes = [
		{ id: 'Task', icon: SquareCheck, color: '#4bce97' },
		{ id: 'Bug', icon: Bug, color: '#f87171' },
		{ id: 'Story', icon: Zap, color: '#f59e0b' }
	];

	async function handleSubmit() {
		if (!title.trim() || columnId === null) return;
		isSubmitting = true;

		await onSave({
			title,
			description,
			columnId,
			priority
		});

		isSubmitting = false;
		close();
	}

	function close() {
		show = false;
		title = '';
		description = '';
		priority = 'Medium';
		issueType = 'Task';
	}
</script>

{#if show}
	<!-- svelte-ignore a11y_click_events_have_key_events -->
	<!-- svelte-ignore a11y_no_static_element_interactions -->
	<div class="jira-backdrop" in:fade={{ duration: 150 }} onclick={close}>
		<div
			class="jira-container"
			in:scale={{ start: 0.98, duration: 200 }}
			onclick={(e) => e.stopPropagation()}
		>
			<header class="jira-header">
				<div class="header-left">
					<div class="breadcrumb">
						{#each issueTypes as type}
							{#if type.id === issueType}
								<type.icon size={14} style="color: {type.color}" fill={type.color} />
							{/if}
						{/each}
						<span class="issue-id">NEW-ISSUE</span>
					</div>
				</div>
				<div class="header-right">
					<button class="icon-btn"><Eye size={16} /><span class="badge">1</span></button>
					<button class="icon-btn"><Share2 size={16} /></button>
					<button class="icon-btn"><Ellipsis size={16} /></button>
					<button class="icon-btn close" onclick={close}><X size={20} /></button>
				</div>
			</header>

			<div class="jira-content">
				<div class="main-column custom-scrollbar">
					<div class="title-section">
						<textarea bind:value={title} placeholder="Issue summary" class="summary-input" rows="1"
						></textarea>

						<div class="action-bar">
							<button class="action-btn"><Paperclip size={14} /> Attach</button>
							<button class="action-btn"><Plus size={14} /> Add subtask</button>
							<button class="action-btn"><Ellipsis size={14} /></button>
						</div>
					</div>

					<div class="description-section">
						<h3>Description</h3>
						<div class="editor-placeholder">
							<textarea bind:value={description} placeholder="Add a description..."></textarea>
						</div>
					</div>

					<div class="activity-section">
						<h3>Activity</h3>
						<div class="tabs">
							<button class="tab active">Comments</button>
							<button class="tab">History</button>
							<button class="tab">Work log</button>
						</div>
						<div class="comment-input-area">
							<div class="user-avatar">JD</div>
							<div class="comment-box">
								<p class="placeholder">Add a comment...</p>
								<div class="comment-footer">
									<span>Pro-tip: press <strong>M</strong> to comment</span>
								</div>
							</div>
						</div>
					</div>
				</div>

				<aside class="side-panel">
					<div class="panel-section">
						<button class="section-toggle" onclick={() => (isDetailsOpen = !isDetailsOpen)}>
							<ChevronDown size={14} class={isDetailsOpen ? '' : 'rotate'} />
							<span>Details</span>
						</button>

						{#if isDetailsOpen}
							<div class="details-grid" transition:slide>
								<div class="detail-row">
									<span class="label">Assignee</span>
									<div class="value">
										<div class="user-pill">
											<User size={12} />
											<span>Unassigned</span>
										</div>
									</div>
								</div>
								<div class="detail-row">
									<span class="label">Reporter</span>
									<div class="value">
										<div class="user-pill">
											<div class="avatar-mini">JD</div>
											<span>John Doe</span>
										</div>
									</div>
								</div>
								<div class="detail-row">
									<span class="label">Priority</span>
									<div class="value">
										<div class="select-field">
											<select bind:value={priority}>
												<option value="HIGH">High</option>
												<option value="MEDIUM">Medium</option>
												<option value="LOW">Low</option>
											</select>
											<ChevronDown size={12} />
										</div>
									</div>
								</div>
								<div class="detail-row">
									<span class="label">Labels</span>
									<div class="value">
										<span class="empty">None</span>
									</div>
								</div>
								<div class="detail-row">
									<span class="label">Due date</span>
									<div class="value">
										<span class="empty">None</span>
									</div>
								</div>
							</div>
						{/if}
					</div>

					<div class="panel-section mt-4">
						<button class="section-toggle">
							<ChevronRight size={14} />
							<span>Development</span>
						</button>
					</div>

					<div class="footer-meta">
						<p>Created just now</p>
						<p>Updated just now</p>
					</div>
				</aside>
			</div>

			<footer class="jira-footer">
				<div class="footer-right">
					<Button variant="ghost" onclick={close}>Cancel</Button>
					<Button variant="primary" disabled={!title.trim() || isSubmitting} onclick={handleSubmit}>
						{isSubmitting ? 'Creating...' : 'Create Issue'}
					</Button>
				</div>
			</footer>
		</div>
	</div>
{/if}

<style>
	.jira-backdrop {
		position: fixed;
		inset: 0;
		background: rgba(0, 0, 0, 0.6);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 1000;
	}

	.jira-container {
		width: 95%;
		max-width: 1000px;
		height: 90vh;
		background: var(--color-marketing-black);
		border-radius: var(--radius-panel);
		border: 1px solid var(--color-border-subtle);
		display: flex;
		flex-direction: column;
		overflow: hidden;
		box-shadow: 0 32px 64px -12px rgba(0, 0, 0, 0.5);
	}

	/* Header Styling */
	.jira-header {
		padding: 12px 20px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		border-bottom: 1px solid var(--color-border-subtle);
	}

	.breadcrumb {
		display: flex;
		align-items: center;
		gap: 8px;
	}

	.issue-id {
		font-size: 12px;
		font-weight: 500;
		color: var(--color-text-tertiary);
	}

	.header-right {
		display: flex;
		gap: 8px;
	}

	.icon-btn {
		position: relative;
		color: var(--color-text-tertiary);
		padding: 6px;
		border-radius: var(--radius-standard);
		transition: all 0.2s;
	}

	.icon-btn:hover {
		background: var(--color-border-subtle);
		color: var(--color-text-primary);
	}
	.icon-btn.close:hover {
		color: #f87171;
	}

	.badge {
		position: absolute;
		top: 2px;
		right: 2px;
		background: var(--color-brand-indigo);
		color: white;
		font-size: 8px;
		width: 12px;
		height: 12px;
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	/* Content Area */
	.jira-content {
		flex: 1;
		display: flex;
		overflow: hidden;
	}

	.main-column {
		flex: 1;
		padding: 24px 32px;
		overflow-y: auto;
		display: flex;
		flex-direction: column;
		gap: 32px;
	}

	.summary-input {
		width: 100%;
		background: transparent;
		border: none;
		color: var(--color-text-primary);
		font-size: 24px;
		font-weight: 600;
		resize: none;
		outline: none;
		padding: 4px 0;
		margin-bottom: 8px;
	}

	.action-bar {
		display: flex;
		gap: 8px;
	}

	.action-btn {
		background: var(--color-border-subtle);
		padding: 6px 12px;
		border-radius: var(--radius-comfortable);
		font-size: 13px;
		color: var(--color-text-secondary);
		display: flex;
		align-items: center;
		gap: 6px;
	}

	.action-btn:hover {
		background: var(--color-border-standard);
	}

	h3 {
		font-size: 14px;
		font-weight: 600;
		color: var(--color-text-secondary);
		margin-bottom: 12px;
	}

	.editor-placeholder textarea {
		width: 100%;
		min-height: 120px;
		background: var(--color-panel-dark);
		border: 1px solid var(--color-border-subtle);
		border-radius: var(--radius-card);
		padding: 12px;
		color: var(--color-text-primary);
		font-size: 14px;
		outline: none;
		resize: vertical;
	}

	/* Activity Section */
	.tabs {
		display: flex;
		gap: 20px;
		border-bottom: 1px solid var(--color-border-subtle);
		margin-bottom: 20px;
	}

	.tab {
		padding: 8px 0;
		font-size: 13px;
		color: var(--color-text-tertiary);
		position: relative;
	}

	.tab.active {
		color: var(--color-brand-indigo);
		font-weight: 600;
	}

	.tab.active::after {
		content: '';
		position: absolute;
		bottom: -1px;
		left: 0;
		width: 100%;
		height: 2px;
		background: var(--color-brand-indigo);
	}

	.comment-input-area {
		display: flex;
		gap: 12px;
	}

	.user-avatar {
		width: 32px;
		height: 32px;
		background: var(--color-accent-violet);
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 12px;
		font-weight: 700;
		color: white;
	}

	.comment-box {
		flex: 1;
		background: var(--color-panel-dark);
		border: 1px solid var(--color-border-subtle);
		border-radius: var(--radius-card);
		padding: 12px;
	}

	.placeholder {
		color: var(--color-text-quaternary);
		font-size: 13px;
	}

	.comment-footer {
		margin-top: 8px;
		font-size: 11px;
		color: var(--color-text-quaternary);
	}

	/* Side Panel Styling */
	.side-panel {
		width: 320px;
		border-left: 1px solid var(--color-border-subtle);
		padding: 24px 20px;
		background: var(--color-marketing-black);
	}

	.section-toggle {
		display: flex;
		align-items: center;
		gap: 8px;
		width: 100%;
		font-size: 13px;
		font-weight: 600;
		color: var(--color-text-secondary);
		margin-bottom: 12px;
	}

	.rotate {
		transform: rotate(-90deg);
	}

	.details-grid {
		display: flex;
		flex-direction: column;
		gap: 16px;
	}

	.detail-row {
		display: flex;
		align-items: center;
	}

	.label {
		width: 100px;
		font-size: 12px;
		color: var(--color-text-tertiary);
	}

	.value {
		flex: 1;
		font-size: 13px;
	}

	.user-pill {
		display: flex;
		align-items: center;
		gap: 8px;
		color: var(--color-text-secondary);
	}

	.avatar-mini {
		width: 20px;
		height: 20px;
		background: var(--color-surface-secondary);
		border-radius: 50%;
		font-size: 9px;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.select-field {
		position: relative;
		display: flex;
		align-items: center;
		gap: 4px;
		color: var(--color-brand-indigo);
	}

	.select-field select {
		position: absolute;
		opacity: 0;
		cursor: pointer;
	}

	.empty {
		color: var(--color-text-quaternary);
	}

	.footer-meta {
		margin-top: 40px;
		padding-top: 20px;
		border-top: 1px solid var(--color-border-subtle);
		font-size: 11px;
		color: var(--color-text-quaternary);
	}

	/* Footer */
	.jira-footer {
		padding: 16px 20px;
		border-top: 1px solid var(--color-border-subtle);
		display: flex;
		justify-content: flex-end;
		background: var(--color-panel-dark);
	}

	.footer-right {
		display: flex;
		gap: 12px;
	}

	/* Custom Scrollbar */
	.custom-scrollbar::-webkit-scrollbar {
		width: 6px;
	}
	.custom-scrollbar::-webkit-scrollbar-thumb {
		background: var(--color-border-subtle);
		border-radius: 10px;
	}
</style>
