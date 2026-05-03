import { http } from '$lib/http/http';
import type { User } from '$lib/interface/user';
import { redirect, type Handle } from '@sveltejs/kit';

export const handle: Handle = async ({ event, resolve }) => {
	if (!event.route.id) return resolve(event);
	const accessToken = event.cookies.get('accessToken');
	const originFetch = event.fetch;
	event.fetch = async (input, init) => {
		const cookie = event.request.headers.get('cookie');
		if (cookie) {
			const headers = new Headers(init?.headers);
			headers.set('cookie', cookie);
			return originFetch(input, { ...init, headers });
		}
		return originFetch(input, init);
	};
	if (accessToken) {
		const response = await http(event.fetch).get<User>(`/users/me`);
		if (response.data) {
			event.locals.user = response.data;
		} else {
			console.log('Failed to fetch user: ', response.error);
		}
	}

	const isPrivateRoute = event.route.id?.includes('(private)');
	const isAuthPage = event.url.pathname === '/login' || event.url.pathname === '/register';
	if (isPrivateRoute && !event.locals.user) {
		throw redirect(302, '/login');
	}
	if (isAuthPage && event.locals.user) {
		throw redirect(302, '/dashboard');
	}
	if (event.url.pathname === '/' && event.locals.user) {
		throw redirect(302, '/dashboard');
	}
	return resolve(event);
};
