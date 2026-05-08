<script lang="ts">
	import { Plus, MoreHorizontal } from 'lucide-svelte';
	import KanbanTask from './KanbanTask.svelte';
	import type { ColumnDetail } from '$lib/interface/column';
	import Input from '../ui/Input.svelte';
	import type { CreateTaskRequest } from '$lib/services/task.service';
	import type { Task } from '$lib/interface/task';

	let { column, onAddTask, onTaskClick } = $props<{
		column: ColumnDetail;
		onAddTask: (taskData: CreateTaskRequest) => void;
		onTaskClick: (task: Task) => void;
	}>();

	let isAdding = $state<boolean>(false);

	let newTaskTitle = $state<string>('');
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

	<div class="column-content custom-scrollbar">
		{#each column.tasks as task (task.id)}
			<KanbanTask {task} {onTaskClick} />
		{/each}

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
		padding: 0 12px 16px 12px;
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
		margin-top: 4px;
	}

	.add-task-btn:hover {
		background: var(--color-border-subtle);
		color: var(--color-text-secondary);
	}

	/* Custom Scrollbar */
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
