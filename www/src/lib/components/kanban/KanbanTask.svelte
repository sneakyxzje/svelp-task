<script lang="ts">
	import type { Task } from '$lib/interface/task';
	import {
		MessageSquare,
		Clock,
		ChevronUp,
		ChevronsUp,
		ChevronDown,
		GripVertical
	} from 'lucide-svelte';

	interface Props {
		task: Task;
		onTaskClick: (t: Task) => void;
	}
	let { task, onTaskClick }: Props = $props();
	import { combine } from '@atlaskit/pragmatic-drag-and-drop/combine';
	import {
		draggable,
		dropTargetForElements
	} from '@atlaskit/pragmatic-drag-and-drop/element/adapter';
	import {
		attachClosestEdge,
		extractClosestEdge
	} from '@atlaskit/pragmatic-drag-and-drop-hitbox/closest-edge';
	import type { Edge } from '@atlaskit/pragmatic-drag-and-drop-hitbox/closest-edge';
	const getPriorityIcon = (p: string) => {
		if (p === 'HIGH') return { icon: ChevronsUp, color: '#f87171' };
		if (p === 'MEDIUM') return { icon: ChevronUp, color: '#ff991f' };
		return { icon: ChevronDown, color: '#4bce97' };
	};

	let taskEl: HTMLElement | null = $state(null);
	let pInfo = $derived(getPriorityIcon(task.priority));
	let isDragging = $state<boolean>(false);
	let closestEdge: Edge | null = $state(null);
	$effect(() => {
		if (!taskEl) return;

		return combine(
			draggable({
				element: taskEl,
				getInitialData: () => ({
					taskId: task.id,
					columnId: task.columnId,
					type: 'task'
				}),
				onDragStart: () => {
					isDragging = true;
				},
				onDrop: () => {
					isDragging = false;
				}
			}),

			dropTargetForElements({
				element: taskEl,
				getData: ({ input, element }) => {
					const data = { taskId: task.id, columnId: task.columnId, type: 'task' };
					return attachClosestEdge(data, {
						input,
						element,
						allowedEdges: ['top', 'bottom']
					});
				},
				onDragEnter: ({ source, self }) => {
					if (source.data.taskId === task.id) return;
					closestEdge = extractClosestEdge(self.data);
				},
				onDragLeave: () => {
					closestEdge = null;
				},
				onDrop: () => {
					closestEdge = null;
				}
			})
		);
	});
</script>

<div class="task-card {isDragging ? 'is-dragging' : ''}" bind:this={taskEl}>
	{#if closestEdge === 'top'}
		<div class="drop-indicator-top"></div>
	{/if}
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

	{#if closestEdge === 'bottom'}
		<div class="drop-indicator-bottom"></div>
	{/if}
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

	.task-card.is-dragging {
		opacity: 0.4;
		box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4);
		border-style: dashed;
		border-color: #8777d9;
		cursor: grabbing;
	}

	.drop-indicator-top {
		position: absolute;
		top: -3px;
		left: 0;
		right: 0;
		height: 3px;
		background-color: #4c9aff;
		border-radius: 2px;
		z-index: 10;
		pointer-events: none;
	}

	.drop-indicator-bottom {
		position: absolute;
		bottom: -3px;
		left: 0;
		right: 0;
		height: 3px;
		background-color: #4c9aff;
		border-radius: 2px;
		z-index: 10;
		pointer-events: none;
	}
</style>
