import { http } from '$lib/http/http';
import type { Board } from '$lib/interface/board';
import type { Workspace } from '$lib/interface/workspace';
import { error } from '@sveltejs/kit';

export const load = async ({ fetch, params }) => {
	const { slug } = params;

	const wsRes = await http(fetch).get<Workspace>(`/workspaces/${slug}`);
	if (wsRes.error) {
		throw error(404, 'Not found');
	}

	const boardsRes = await http(fetch).get<Board[]>(`/boards?workspaceId=${wsRes.data?.id}`);

	return {
		workspace: wsRes.data,
		boards: boardsRes.data || []
	};
};
