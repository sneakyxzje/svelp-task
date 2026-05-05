import { http } from '$lib/http/http';
import type { BoardDetail } from '$lib/interface/board';

export const boardService = {
	getDetail: (workspaceSlug: string, boardSlug: string) =>
		http.get<BoardDetail>(`/boards/${workspaceSlug}/${boardSlug}`)
};
