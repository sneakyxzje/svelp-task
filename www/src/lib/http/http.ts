type Fetcher = typeof fetch;
const BASE_URL = 'http://localhost:8080/api';
type QueryParamValue = string | number | boolean | undefined;
type QueryParams = Record<string, QueryParamValue>;
const createMethods = (f: Fetcher) => {
	return {
		async post<T>(
			url: string,
			rawBody: unknown
		): Promise<{ data: T | null; error: string | null }> {
			let headers: Record<string, string> = {};
			let body: string;

			if (rawBody !== null && typeof rawBody === 'object') {
				body = JSON.stringify(rawBody);
				headers['Content-Type'] = 'application/json';
			} else {
				body = JSON.stringify({ value: rawBody });
				headers['Content-Type'] = 'application/json';
			}

			try {
				const response = await f(BASE_URL + url, {
					method: 'POST',
					headers,
					body: body,
					credentials: 'include'
				});

				if (!response.ok) {
					return { data: null, error: `Error Status: ${response.status}` };
				}
				const data = await response.json();
				return { data: data as T, error: null };
			} catch (err) {
				return { data: null, error: 'Unknown error' };
			}
		},
		async get<T>(
			rawUrl: string,
			params?: QueryParams
		): Promise<{ data: T | null; error: string | null }> {
			const searchParams = new URLSearchParams();
			if (params) {
				Object.entries(params).forEach(([key, value]) => {
					if (value !== undefined && value !== null) {
						searchParams.append(key, String(value));
					}
				});
			}
			const query = searchParams.toString();
			const url = query ? `${BASE_URL}${rawUrl}?${query}` : `${BASE_URL}${rawUrl}`;

			try {
				const response = await f(url, {
					method: 'GET',
					headers: {
						'Content-Type': 'application/json'
					},
					credentials: 'include'
				});
				if (!response.ok) {
					return { data: null, error: `Error Status: ${response.status}` };
				}
				const data = await response.json();
				return { data: data as T, error: null };
			} catch (err) {
				return { data: null, error: 'Unknown Error' };
			}
		},
		async patch<T>(
			url: string,
			rawBody: unknown
		): Promise<{ data: T | null; error: string | null }> {
			let headers: Record<string, string> = {};
			let body: string;

			if (rawBody !== null && typeof rawBody === 'object') {
				body = JSON.stringify(rawBody);
				headers['Content-Type'] = 'application/json';
			} else {
				body = JSON.stringify({ value: rawBody });
				headers['Content-Type'] = 'application/json';
			}

			try {
				const response = await f(BASE_URL + url, {
					method: 'PATCH',
					headers,
					body: body,
					credentials: 'include'
				});

				if (!response.ok) {
					return { data: null, error: `Error Status: ${response.status}` };
				}
				const data = await response.json();
				return { data: data as T, error: null };
			} catch (err) {
				return { data: null, error: 'Unknown error' };
			}
		}
	};
};

const callable = (f: Fetcher) => createMethods(f);
const defaultMethods = createMethods(
	typeof fetch !== 'undefined'
		? fetch
		: ((() => {
				throw new Error('Need fetch when in SSR!');
			}) as any)
);
export const http = Object.assign(callable, defaultMethods);
