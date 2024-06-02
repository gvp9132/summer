import { createSlice, PayloadAction} from '@reduxjs/toolkit';

import { StatePagingType } from 'storage/redux';
import RequestStatus from 'storage/redux/RequestStatus';
import {builderPagingHandler} from "storage/redux/reducer";
import {fetchAddSecurityUserMenu, fetchDeleteSecurityUserMenu, fetchUpdateSecurityUserMenu, fetchSecurityUserMenuPageData} 
    from "storage/redux/async-action/securityUserMenuAsyncAction";
import UserStorage from "storage/local/UserStorage";

import {SecurityUserMenu} from "type/entity/SecurityUserMenuUser";

/**
 * 用户菜单关系表状态管理类型
 */
export type SecurityUserMenuState = StatePagingType<SecurityUserMenu>

/** 状态初始化数据 */
const initialState: SecurityUserMenu = {
    data: [],
    loading: false,
    requestStatus: RequestStatus.INIT,
    currentPage: 1,
    pageSize: UserStorage.getInstance().getAdminPageConfigFromLocalStore().securityUserMenuPageSize,
    total: 0,
    totalPage: 10
};

export const securityUserMenuSlice = createSlice({
    name: 'securityUserMenu',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builderPagingHandler(builder, fetchSecurityUserMenuPageData);
        /** 添加用户菜单关系表信息 */
        builder.addCase(fetchAddSecurityUserMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 修改用户菜单关系表信息 */
        builder.addCase(fetchUpdateSecurityUserMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 用户菜单关系表用户信息 */
        builder.addCase(fetchDeleteSecurityUserMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
    }
});

export const {} = securityUserMenuSlice.actions;
export default securityUserMenuSlice.reducer;

