import jwt_decoder from 'jwt-decode'
import moment from 'moment'
import axios from 'axios'
import { Roles } from '../constants'
import router from '../router/index'

export function setToken(token) {
    sessionStorage.setItem('token', token);
}

export function removeToken() {
    sessionStorage.removeItem('token');
}

export function setAuthorizationHeaderInterceptor() {
	// Set Authorization header interceptor
	axios.interceptors.request.use(config => {
		config.headers.Authorization = sessionStorage.getItem('token') ? `Bearer ${sessionStorage.getItem('token')}` : '';
		return config;
	});
}

export function setUnauthorizedHeaderInterceptor(store) {
	// Set Unauthorized handler
	axios.interceptors.response.use(function (response) {
		return response;
	}, function (error) {
		if ((401 === error.response.status || 403 == error.response.status) && sessionStorage.getItem('token')) {
			store.dispatch('authentication/logOut');
			router.push('/auth')
		}
		else {
			return Promise.reject(error);
		}
	});
	
}

export function hasTokenExpired() {
	return decodeToken()?.exp < moment().unix();
}


export function getRoleFromToken() {
	if(hasTokenExpired()) {
		return null;
	}

    const roleFromToken = decodeToken()?.role;

	if(roleFromToken === undefined) {
        return [];
    }
	const splittedRoles = roleFromToken.split(",");

	const roles = []
	splittedRoles.forEach(role => roles.push(Object.keys(Roles).find(r => Roles[r] === role)))

	return splittedRoles
}

export function getIdFromToken() {
	if(hasTokenExpired()) {
		return null;
	}

    return decodeToken()?.id;
}

export function isUserLoggedIn() {
    return getRoleFromToken() && !hasTokenExpired();
}


function decodeToken() {
	const token = sessionStorage.getItem('token');
	return token ? jwt_decoder(token) : null;
}