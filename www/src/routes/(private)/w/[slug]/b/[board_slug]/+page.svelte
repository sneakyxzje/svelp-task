<script lang="ts">
	import { Plus, Filter, Search, Share2, MoreHorizontal } from 'lucide-svelte';
	import { fade } from 'svelte/transition';
	import Button from '$lib/components/ui/Button.svelte';
	import { columnService } from '$lib/services/column.service.js';
	import { taskService } from '$lib/services/task.service';
	import UpdateTaskModal from '$lib/components/modals/UpdateTaskModal.svelte';
	import KanbanColumn from '$lib/components/kanban/KanbanColumn.svelte';
	import type { CreateTaskRequest, Task, UpdateTaskRequest } from '$lib/interface/task.js';

	const { data } = $props();
	let board = $state(data.board);

	let sortedColumns = $derived(
		board?.columns ? [...board.columns].sort((a, b) => a.position - b.position) : []
	);

	const handleAddColumn = async () => {
		if (!board) return;
		const { data: newColumn, error } = await columnService.create({
			name: 'New Column',
			boardId: board.id
		});
		if (newColumn) {
			const columnWithTasks = { ...newColumn, tasks: [] };
			board.columns = [...board.columns, columnWithTasks];
		}
	};

	let updateTaskModalVisible = $state(false);
	let activeColumnId = $state<number | null>(null);
	let selectedTask = $state<Task | null>(null);

	const selectTask = (task: Task) => {
		selectedTask = task;
		activeColumnId = task.columnId;
		updateTaskModalVisible = true;
	};

	const addTask = async (taskData: CreateTaskRequest) => {
		if (!board) return;
		const { data: newTask, error } = await taskService.create(taskData);
		if (newTask) {
			board.columns = board.columns.map((col) => {
				if (col.id === taskData.columnId) {
					return { ...col, tasks: [...col.tasks, newTask] };
				}
				return col;
			});
		}
	};

	const updateTask = async (taskData: UpdateTaskRequest) => {
		if (!board) return;
		const { data: updateTask, error } = await taskService.update(taskData);
		if (updateTask) {
			board.columns = board.columns.map((col) => {
				if (col.id === updateTask.columnId) {
					return {
						...col,
						tasks: col.tasks.map((t) => (t.id === updateTask.id ? updateTask : t))
					};
				}
				return col;
			});
		}
	};

	$effect(() => {
		board = data.board;
	});
</script>

<div class="board-wrapper" in:fade={{ duration: 200 }}>
	<div class="board-toolbar">
		<div class="toolbar-left">
			<div class="search-box">
				<Search size={14} class="text-text-quaternary" />
				<input type="text" placeholder="Filter tasks..." />
			</div>
			<div class="v-divider"></div>
			<div class="avatar-group">
				<div class="avatar">JD</div>
				<div class="plus-btn"><Plus size={12} /></div>
			</div>
		</div>

		<div class="toolbar-right">
			<Button variant="ghost" class="h-8 text-[13px] text-text-tertiary">
				<Filter size={14} class="mr-2" /> Filter
			</Button>
			<div class="v-divider"></div>
			<Button variant="primary" class="h-8 text-[13px]" onclick={handleAddColumn}>
				<Plus size={14} class="mr-1.5" /> Add Column
			</Button>
			<button class="more-btn">
				<MoreHorizontal size={16} />
			</button>
		</div>
	</div>

	<main class="kanban-scroll-area custom-scrollbar">
		<div class="kanban-canvas">
			{#each sortedColumns as column (column.id)}
				<KanbanColumn
					{column}
					onAddTask={addTask}
					onTaskClick={(task) => selectTask(task)}
					onTasksChange={(newTasks) => {
						const col = board?.columns.find((c) => c.id === column.id);
						if (col) col.tasks = newTasks;
					}}
				/>
			{/each}

			{#if sortedColumns.length === 0}
				<div class="empty-state">
					<p>No columns yet. Start by adding one!</p>
					<Button variant="outline" onclick={handleAddColumn}>Create first column</Button>
				</div>
			{/if}
		</div>
	</main>
</div>

<UpdateTaskModal
	bind:show={updateTaskModalVisible}
	columnId={activeColumnId}
	onSave={updateTask}
	task={selectedTask}
/>

<style>
	.board-wrapper {
		display: flex;
		flex-direction: column;
		height: 100%;
		background: var(--color-marketing-black);
	}

	/* Toolbar Styling */
	.board-toolbar {
		height: 56px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 0 24px;
		background: var(--color-marketing-black);
		border-bottom: 1px solid var(--color-border-subtle);
	}

	.toolbar-left,
	.toolbar-right {
		display: flex;
		align-items: center;
		gap: 16px;
	}

	.search-box {
		display: flex;
		align-items: center;
		gap: 8px;
		background: var(--color-panel-dark);
		border: 1px solid var(--color-border-subtle);
		padding: 4px 12px;
		border-radius: var(--radius-comfortable);
		width: 240px;
	}

	.search-box input {
		background: transparent;
		border: none;
		outline: none;
		color: var(--color-text-primary);
		font-size: 0.8125rem;
		width: 100%;
	}

	.v-divider {
		width: 1px;
		height: 20px;
		background: var(--color-border-subtle);
	}

	.avatar-group {
		display: flex;
		align-items: center;
	}

	.avatar,
	.plus-btn {
		width: 26px;
		height: 26px;
		border-radius: 50%;
		border: 2px solid var(--color-marketing-black);
		background: var(--color-surface-secondary);
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 0.65rem;
		font-weight: 700;
		color: var(--color-text-secondary);
		margin-left: -8px;
	}

	.avatar:first-child {
		margin-left: 0;
	}

	.plus-btn {
		background: transparent;
		border: 1px dashed var(--color-border-standard);
		color: var(--color-text-quaternary);
	}

	.more-btn {
		color: var(--color-text-quaternary);
		padding: 6px;
		border-radius: var(--radius-standard);
		transition: all 0.2s;
	}

	.more-btn:hover {
		background: var(--color-border-subtle);
		color: var(--color-text-primary);
	}

	/* Kanban Layout */
	.kanban-scroll-area {
		flex: 1;
		overflow-x: auto;
		overflow-y: hidden;
		padding: 24px;
	}

	.kanban-canvas {
		display: flex;
		gap: 20px;
		height: 100%;
		align-items: flex-start;
	}

	.empty-state {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		gap: 16px;
		color: var(--color-text-tertiary);
		font-size: 0.875rem;
		border: 2px dashed var(--color-border-subtle);
		border-radius: var(--radius-panel);
		margin: 20px;
	}

	/* Custom Scrollbar */
	.custom-scrollbar::-webkit-scrollbar {
		height: 8px;
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
