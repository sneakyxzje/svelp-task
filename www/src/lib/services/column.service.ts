import { http } from '$lib/http/http';
import type { ColumnDetail } from '$lib/interface/column';

export const columnService = {
	create: (data: { name: string; boardId: number }) =>
		http.post<ColumnDetail>('/columns', data)
};
