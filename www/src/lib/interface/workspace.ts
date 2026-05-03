export interface Workspace {
	id: number;
	name: string;
	slug: string;
	description?: string;
	icon?: string;
	ownerId: number;
}
