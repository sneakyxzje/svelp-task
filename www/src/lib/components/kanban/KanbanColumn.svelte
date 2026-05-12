<script lang="ts">
	import { Plus, MoreHorizontal } from 'lucide-svelte';
	import KanbanTask from './KanbanTask.svelte';
	import type { ColumnDetail } from '$lib/interface/column';
	import Input from '../ui/Input.svelte';
	import type { CreateTaskRequest, Task } from '$lib/interface/task';
	import { dndzone, TRIGGERS, type DndEvent } from 'svelte-dnd-action';
	import { taskService } from '$lib/services/task.service';

	let { column, onTasksChange, onAddTask, onTaskClick } = $props<{
		column: ColumnDetail;
		onAddTask: (taskData: CreateTaskRequest) => void;
		onTasksChange: (newTasks: Task[]) => void;
		onTaskClick: (task: Task) => void;
	}>();
	let tasks = $state.raw<Task[]>($state.snapshot(column.tasks) as Task[]);

	let isAdding = $state<boolean>(false);

	let newTaskTitle = $state<string>('');

	const handleDndConsider = (e: CustomEvent<DndEvent>) => {
		tasks = [...e.detail.items] as Task[];
	};

	const handleDndFinalize = async (e: CustomEvent<DndEvent>) => {
		const { items, info } = e.detail;
		tasks = [...items] as Task[];

		if (info.trigger === TRIGGERS.DROPPED_INTO_ZONE) {
			console.log(tasks);
			const movedTaskId = info.id;

			const index = tasks.findIndex((t) => String(t.id) === String(movedTaskId));

			if (index !== -1) {
				const prevTask = index > 0 ? tasks[index - 1] : null;
				const nextTask = index < tasks.length - 1 ? tasks[index + 1] : null;

				const prevPos = prevTask ? prevTask.position : null;
				const nextPos = nextTask ? nextTask.position : null;
				const response = await taskService.move(Number(movedTaskId), {
					targetColumnId: column.id,
					prevTaskPosition: prevPos,
					nextTaskPosition: nextPos
				});
				if (response.error) {
					console.error(response.error);
				} else {
					onTasksChange(tasks);
				}
			}
		}
	};
</script>

<section class="kanban-column">
	<header class="column-header">
		<div class="column-title">
			<span class="count">{column.tasks?.length ?? 0}</span>
			<h2>{column.name}</h2>
		</div>
		<div class="column-actions">
			<button onclick={() => onAddTask(column.id)} class="icon-btn">
				<Plus size={16} />
			</button>
			<button class="icon-btn">
				<MoreHorizontal size={16} />
			</button>
		</div>
	</header>

	<div
		class="column-content custom-scrollbar"
		use:dndzone={{
			items: tasks,
			flipDurationMs: 0,
			transformDraggedElement: (el) => {
				if (!el) return;
				el.style.transform = 'rotate(2deg) scale(1.02)';
				el.style.boxShadow = '0 16px 32px rgba(0,0,0,0.4)';
				el.style.cursor = 'grabbing';
			},
			morphDisabled: true,
			dropTargetStyle: {}
		}}
		onconsider={handleDndConsider}
		onfinalize={handleDndFinalize}
	>
		{#each tasks as task (task.id)}
			<KanbanTask {task} {onTaskClick} />
		{/each}
	</div>

	<div class="column-footer">
		<button
			onclick={() => {
				isAdding = true;
			}}
			class="add-task-btn"
		>
			{#if isAdding}
				<Input
					bind:value={newTaskTitle}
					placeholder="Task title..."
					onkeydown={(e: KeyboardEvent) => {
						if (e.key === 'Enter' && newTaskTitle.trim()) {
							onAddTask({
								title: newTaskTitle,
								columnId: column.id
							});
							newTaskTitle = '';
							isAdding = false;
						} else if (e.key === 'Escape') {
							newTaskTitle = '';
							isAdding = false;
						}
					}}
				/>
			{:else}
				<Plus size={14} />
				<span>Add task</span>
			{/if}
		</button>
	</div>
</section>

<style>
	.kanban-column {
		width: 280px;
		flex-shrink: 0;
		display: flex;
		flex-direction: column;
		max-height: 100%;
		background: var(--color-panel-dark);
		border-radius: var(--radius-panel);
		border: 1px solid var(--color-border-subtle);
	}

	.column-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 16px;
	}

	.column-title {
		display: flex;
		align-items: center;
		gap: 8px;
	}

	.column-title h2 {
		font-size: 0.8125rem;
		font-weight: 600;
		color: var(--color-text-secondary);
		letter-spacing: -0.01em;
	}

	.count {
		font-size: 0.7rem;
		font-weight: 700;
		color: var(--color-text-quaternary);
		background: var(--color-border-subtle);
		padding: 2px 6px;
		border-radius: var(--radius-micro);
		min-width: 20px;
		text-align: center;
	}

	.icon-btn {
		color: var(--color-text-quaternary);
		padding: 4px;
		border-radius: var(--radius-standard);
		transition: all 0.2s;
	}

	.icon-btn:hover {
		background: var(--color-border-subtle);
		color: var(--color-text-primary);
	}

	.column-content {
		display: flex;
		flex-direction: column;
		gap: 8px;
		overflow-y: auto;
		flex: 1;
		padding: 8px 12px;
		min-height: 60px;
	}

	.column-footer {
		padding: 4px 12px 12px 12px;
		flex-shrink: 0;
	}

	.add-task-btn {
		display: flex;
		align-items: center;
		gap: 8px;
		width: 100%;
		padding: 8px;
		color: var(--color-text-quaternary);
		font-size: 0.8125rem;
		font-weight: 500;
		border-radius: var(--radius-comfortable);
		transition: all 0.2s;
	}

	.add-task-btn:hover {
		background: var(--color-border-subtle);
		color: var(--color-text-secondary);
	}

	.custom-scrollbar::-webkit-scrollbar {
		width: 4px;
	}
	.custom-scrollbar::-webkit-scrollbar-track {
		background: transparent;
	}
	.custom-scrollbar::-webkit-scrollbar-thumb {
		background: var(--color-border-subtle);
		border-radius: 10px;
	}
	.custom-scrollbar::-webkit-scrollbar-thumb:hover {
		background: var(--color-border-standard);
	}
</style>
