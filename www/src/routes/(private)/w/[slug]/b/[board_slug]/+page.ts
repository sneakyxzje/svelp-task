import { http } from '$lib/http/http.js';
import type { BoardDetail } from '$lib/interface/board.js';

export const load = async ({ params, fetch }) => {
	const { slug, board_slug } = params;
	const response = await http(fetch).get<BoardDetail>(`/boards/${slug}/${board_slug}`);
	if (!response.data) {
		return { board: null };
	}
	console.log(response.data);
	return {
		board: response.data
	};
};
