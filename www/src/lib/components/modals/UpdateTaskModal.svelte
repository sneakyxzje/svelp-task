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
	import type { CreateTaskRequest, UpdateTaskRequest } from '$lib/services/task.service';
	import type { Task } from '$lib/interface/task';

	let {
		show = $bindable(),
		columnId,
		onSave,
		task
	} = $props<{
		show: boolean;
		columnId: number | null;
		onSave: (data: UpdateTaskRequest) => Promise<void>;
		task: Task | null;
	}>();

	// Form State
	let title = $state('');
	let description = $state('');
	let priority = $state('MEDIUM');
	let status = $state('IN_PROGRESS');
	let issueType = $state('Task');
	let assigneeId = $state<number | null>(null);
	let dueDate = $state<string | null>(null);
	let isSubmitting = $state(false);

	// UI State
	let isDetailsOpen = $state(true);
	let isDevOpen = $state(false);

	const issueTypes = [
		{ id: 'Task', icon: SquareCheck, color: '#4bce97' },
		{ id: 'Bug', icon: Bug, color: '#f87171' },
		{ id: 'Story', icon: Zap, color: '#f59e0b' }
	];

	const statuses = [
		{ id: 'TO_DO', label: 'To Do', color: '#42526e' },
		{ id: 'IN_PROGRESS', label: 'In Progress', color: '#0052cc' },
		{ id: 'DONE', label: 'Done', color: '#00875a' }
	];

	async function handleSubmit() {
		if (!title.trim() || columnId === null || !task) return;
		isSubmitting = true;

		await onSave({
			id: task.id,
			title,
			description,
			columnId,
			priority,
			assigneeId,
			dueDate
		});

		isSubmitting = false;
		close();
	}

	function close() {
		show = false;
	}

	$effect(() => {
		if (task) {
			title = task.title;
			description = task.description || '';
			priority = task.priority || 'MEDIUM';
			assigneeId = task.assigneeId;
			dueDate = task.dueDate;
		}
	});
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
						<span class="issue-id">{task ? `SV-${task.id}` : 'NEW-ISSUE'}</span>
					</div>
				</div>
				<div class="header-right">
					<button class="icon-btn"><Eye size={16} /></button>
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
							<button class="action-btn"><Share2 size={14} /> Link issue</button>
							<button class="action-btn"><Ellipsis size={14} /></button>
						</div>
					</div>

					<div class="description-section">
						<h3>Description</h3>
						<div class="editor-area">
							<textarea bind:value={description} placeholder="Add a description..." class="description-input"></textarea>
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

				<aside class="side-panel custom-scrollbar">
					<div class="status-section">
						<div class="status-dropdown">
							{#each statuses as s}
								{#if s.id === status}
									<div class="status-trigger" style="background: {s.color}20; color: {s.color}">
										<span>{s.label}</span>
										<ChevronDown size={14} />
									</div>
								{/if}
							{/each}
						</div>
					</div>

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
											<input type="number" bind:value={assigneeId} class="inline-input" placeholder="Unassigned" />
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
											<span>{priority}</span>
											<ChevronDown size={12} />
										</div>
									</div>
								</div>
								<div class="detail-row">
									<span class="label">Due date</span>
									<div class="value">
										<input type="date" bind:value={dueDate} class="inline-input" />
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
							</div>
						{/if}
					</div>

					<div class="panel-section mt-4">
						<button class="section-toggle" onclick={() => (isDevOpen = !isDevOpen)}>
							<ChevronDown size={14} class={isDevOpen ? '' : 'rotate'} />
							<span>Development</span>
						</button>
						{#if isDevOpen}
							<div class="dev-content" transition:slide>
								<button class="dev-btn"><Plus size={14} /> Create branch</button>
							</div>
						{/if}
					</div>

					<div class="footer-meta">
						<p>Created {task ? new Date(task.createdAt).toLocaleString() : 'just now'}</p>
						<p>Updated just now</p>
					</div>
				</aside>
			</div>

			<footer class="jira-footer">
				<div class="footer-right">
					<Button variant="ghost" onclick={close}>Cancel</Button>
					<Button variant="primary" disabled={!title.trim() || isSubmitting} onclick={handleSubmit}>
						{isSubmitting ? 'Saving...' : 'Save Changes'}
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
		border: 2px solid transparent;
		color: var(--color-text-primary);
		font-size: 24px;
		font-weight: 600;
		resize: none;
		outline: none;
		padding: 4px 8px;
		margin-left: -8px;
		border-radius: var(--radius-standard);
		transition: all 0.2s;
	}

	.summary-input:focus {
		background: var(--color-panel-dark);
		border-color: var(--color-brand-indigo);
	}

	.action-bar {
		display: flex;
		flex-wrap: wrap;
		gap: 4px;
		margin-top: 12px;
	}

	.action-btn {
		background: var(--color-border-subtle);
		padding: 6px 10px;
		border-radius: 3px;
		font-size: 13px;
		font-weight: 500;
		color: var(--color-text-secondary);
		display: flex;
		align-items: center;
		gap: 8px;
		transition: all 0.2s;
	}

	.action-btn:hover {
		background: var(--color-border-standard);
	}

	.editor-area {
		margin-top: 8px;
	}

	.description-input {
		width: 100%;
		min-height: 120px;
		background: transparent;
		border: 1px solid transparent;
		border-radius: var(--radius-card);
		padding: 8px;
		color: var(--color-text-primary);
		font-size: 14px;
		outline: none;
		resize: vertical;
		transition: all 0.2s;
	}

	.description-input:focus {
		background: var(--color-panel-dark);
		border-color: var(--color-brand-indigo);
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
		width: 340px;
		padding: 24px 20px;
		background: var(--color-marketing-black);
		overflow-y: auto;
	}

	.status-section {
		margin-bottom: 24px;
	}

	.status-trigger {
		display: inline-flex;
		align-items: center;
		gap: 8px;
		padding: 6px 12px;
		border-radius: 3px;
		font-size: 13px;
		font-weight: 700;
		text-transform: uppercase;
		cursor: pointer;
		transition: filter 0.2s;
	}

	.status-trigger:hover {
		filter: brightness(1.2);
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
		padding: 4px 0;
	}

	.rotate {
		transform: rotate(-90deg);
	}

	.details-grid {
		display: flex;
		flex-direction: column;
		gap: 8px;
	}

	.detail-row {
		display: flex;
		align-items: center;
		min-height: 32px;
	}

	.label {
		width: 120px;
		font-size: 13px;
		font-weight: 500;
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
		padding: 4px 8px;
		border-radius: 3px;
		transition: background 0.2s;
	}

	.user-pill:hover {
		background: var(--color-border-subtle);
	}

	.avatar-mini {
		width: 24px;
		height: 24px;
		background: var(--color-surface-secondary);
		border-radius: 50%;
		font-size: 10px;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.select-field {
		position: relative;
		display: inline-flex;
		align-items: center;
		gap: 4px;
		padding: 4px 8px;
		border-radius: 3px;
		color: var(--color-text-secondary);
		transition: background 0.2s;
	}

	.select-field:hover {
		background: var(--color-border-subtle);
	}

	.select-field select {
		position: absolute;
		inset: 0;
		opacity: 0;
		cursor: pointer;
	}

	.dev-content {
		padding-left: 22px;
	}

	.dev-btn {
		width: 100%;
		display: flex;
		align-items: center;
		gap: 8px;
		padding: 8px;
		font-size: 13px;
		color: var(--color-text-secondary);
		border-radius: 3px;
		transition: background 0.2s;
	}

	.dev-btn:hover {
		background: var(--color-border-subtle);
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

	.inline-input {
		background: transparent;
		border: none;
		color: var(--color-text-secondary);
		font-size: 13px;
		padding: 0;
		width: 100%;
		outline: none;
	}

	.inline-input:hover {
		background: var(--color-panel-dark);
		border-color: var(--color-border-subtle);
	}

	.inline-input:focus {
		background: var(--color-panel-dark);
		border-color: var(--color-brand-indigo);
	}

	/* Xóa mũi tên tăng giảm của input number */
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
		-webkit-appearance: none;
		margin: 0;
	}
</style>
