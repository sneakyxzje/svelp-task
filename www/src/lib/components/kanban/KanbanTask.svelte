<script lang="ts">
	import { MessageSquare, Clock, GripVertical } from 'lucide-svelte';
	import { fly } from 'svelte/transition';
	import type { Task } from '$lib/interface/task';
	import { getPriorityColor } from '$lib/utils/color';

	let { task } = $props<{ task: Task }>();
</script>

<div class="task-card" in:fly={{ y: 10, duration: 300 }}>
	<div class="drag-handle">
		<GripVertical size={14} />
	</div>
	
	<div class="task-content">
		<div class="task-header">
			<span class="task-id">SV-{task.id}</span>
			<div class="priority-dot {getPriorityColor(task.priority).split(' ')[0]}"></div>
			<span class="priority-label">{task.priority}</span>
		</div>

		<h3 class="task-title">{task.title}</h3>

		<div class="task-footer">
			<div class="meta-info">
				{#if task.dueDate}
					<div class="meta-item date">
						<Clock size={12} />
						<span>{new Date(task.dueDate).toLocaleDateString('en-US', { month: 'short', day: 'numeric' })}</span>
					</div>
				{/if}
				<div class="meta-item">
					<MessageSquare size={12} />
					<span>0</span>
				</div>
			</div>
			
			<div class="assignee-area">
				<div class="avatar-sm">U</div>
			</div>
		</div>
	</div>
</div>

<style>
	.task-card {
		position: relative;
		background: var(--color-surface-level-3);
		border: 1px solid var(--color-border-subtle);
		border-radius: var(--radius-card);
		padding: 12px;
		padding-left: 8px;
		cursor: grab;
		display: flex;
		gap: 4px;
		transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
		user-select: none;
	}

	.task-card:hover {
		border-color: var(--color-border-standard);
		background: var(--color-surface-secondary);
		transform: translateY(-1px);
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
	}

	.drag-handle {
		display: flex;
		align-items: flex-start;
		padding-top: 2px;
		color: var(--color-text-quaternary);
		opacity: 0.3;
	}

	.task-card:hover .drag-handle {
		opacity: 1;
		color: var(--color-brand-indigo);
	}

	.task-content {
		flex: 1;
		display: flex;
		flex-direction: column;
		gap: 8px;
	}

	.task-header {
		display: flex;
		align-items: center;
		gap: 6px;
	}

	.task-id {
		font-size: 0.65rem;
		font-weight: 700;
		color: var(--color-text-quaternary);
		letter-spacing: 0.05em;
	}

	.priority-dot {
		width: 6px;
		height: 6px;
		border-radius: 50%;
	}

	.priority-label {
		font-size: 0.7rem;
		color: var(--color-text-tertiary);
		font-weight: 500;
	}

	.task-title {
		font-size: 0.875rem;
		font-weight: 500;
		color: var(--color-text-primary);
		line-height: 1.4;
	}

	.task-footer {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 4px;
	}

	.meta-info {
		display: flex;
		gap: 12px;
	}

	.meta-item {
		display: flex;
		align-items: center;
		gap: 4px;
		font-size: 0.7rem;
		color: var(--color-text-quaternary);
	}

	.meta-item.date {
		color: var(--color-brand-indigo);
	}

	.avatar-sm {
		width: 20px;
		height: 20px;
		border-radius: 50%;
		background: var(--color-accent-violet);
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 0.6rem;
		font-weight: bold;
		color: white;
	}
</style>
