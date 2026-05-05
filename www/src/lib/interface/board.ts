import type { ColumnDetail } from './column';

export interface Board {
	id: number;
	name: string;
	slug: string;
	description: string;
	workspaceId: string;
	createdAt: string;
	updatedAt: string;
}

export interface BoardDetail {
	id: number;
	name: string;
	columns: ColumnDetail[];
}
