<script lang="ts">
	import {
		MessageSquare,
		Clock,
		SquareCheck,
		ChevronUp,
		ChevronsUp,
		ChevronDown,
		GripVertical
	} from 'lucide-svelte';
	import { fly } from 'svelte/transition';
	import type { Task } from '$lib/interface/task';

	let { task, onTaskClick } = $props<{ task: Task; onTaskClick: (t: Task) => void }>();

	const getPriorityIcon = (p: string) => {
		if (p === 'HIGH') return { icon: ChevronsUp, color: '#f87171' };
		if (p === 'MEDIUM') return { icon: ChevronUp, color: '#ff991f' };
		return { icon: ChevronDown, color: '#4bce97' };
	};

	let pInfo = $derived(getPriorityIcon(task.priority));
</script>

<div class="task-card">
	<div class="drag-handle">
		<GripVertical size={14} />
	</div>

	<button class="task-content" onclick={() => onTaskClick(task)}>
		<h3 class="task-title">{task.title}</h3>

		<div class="task-mid">
			<div class="task-id-group">
				<span class="task-id">SV-{task.id}</span>
			</div>
		</div>

		<div class="task-footer">
			<div class="footer-left">
				{#if task.dueDate}
					<div class="date-badge">
						<Clock size={12} />
						<span
							>{new Date(task.dueDate).toLocaleDateString('en-US', {
								month: 'short',
								day: 'numeric'
							})}</span
						>
					</div>
				{/if}
				{#if task.description}
					<MessageSquare size={14} class="text-text-quaternary" />
				{/if}
			</div>

			<div class="footer-right">
				<div class="priority-icon" title={task.priority}>
					<pInfo.icon size={16} style="color: {pInfo.color}" />
				</div>
				<div class="avatar-sm" title="Assignee ID: {task.assigneeId || 'Unassigned'}">
					{task.assigneeId ? 'U' : '?'}
				</div>
			</div>
		</div>
	</button>
</div>

<style>
	.task-card {
		position: relative;
		background: var(--color-surface-level-3);
		border: 1px solid var(--color-border-subtle);
		border-radius: 4px;
		padding: 12px;
		cursor: grab;
		display: flex;
		flex-direction: column;
		gap: 8px;
		transition: all 0.2s;
		user-select: none;
	}

	.task-card:hover {
		background: #2c333f;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
		border-color: var(--color-border-standard);
	}

	.task-content {
		all: unset;
		display: flex;
		flex-direction: column;
		gap: 10px;
		width: 100%;
		cursor: pointer;
	}

	.task-title {
		font-size: 14px;
		color: var(--color-text-primary);
		line-height: 1.4;
		font-weight: 400;
	}

	.task-mid {
		display: flex;
		align-items: center;
		gap: 8px;
	}

	.task-id-group {
		display: flex;
		align-items: center;
		gap: 4px;
	}

	.issue-type-icon {
		flex-shrink: 0;
	}

	.task-id {
		font-size: 12px;
		font-weight: 500;
		color: var(--color-text-tertiary);
	}

	.task-footer {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 4px;
	}

	.footer-left {
		display: flex;
		gap: 8px;
		align-items: center;
	}

	.date-badge {
		display: flex;
		align-items: center;
		gap: 4px;
		background: var(--color-surface-secondary);
		padding: 2px 6px;
		border-radius: 3px;
		font-size: 11px;
		color: var(--color-text-secondary);
		border: 1px solid var(--color-border-subtle);
	}

	.footer-right {
		display: flex;
		align-items: center;
		gap: 10px;
	}

	.priority-icon {
		display: flex;
		align-items: center;
	}

	.avatar-sm {
		width: 24px;
		height: 24px;
		border-radius: 50%;
		background: #8777d9;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 10px;
		font-weight: bold;
		color: white;
	}
</style>
