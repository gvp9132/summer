import { createSlice, PayloadAction} from '@reduxjs/toolkit';

import { StatePagingType } from 'storage/redux';
import RequestStatus from 'storage/redux/RequestStatus';
import {builderPagingHandler} from "storage/redux/reducer";
import {fetchAddSecurityMenu, fetchDeleteSecurityMenu, fetchUpdateSecurityMenu, fetchSecurityMenuPageData} 
    from "storage/redux/async-action/securityMenuAsyncAction";
import UserStorage from "storage/local/UserStorage";

import {SecurityMenu} from "type/entity/SecurityMenuUser";

/**
 * 菜单信息状态管理类型
 */
export type SecurityMenuState = StatePagingType<SecurityMenu>

/** 状态初始化数据 */
const initialState: SecurityMenu = {
    data: [],
    loading: false,
    requestStatus: RequestStatus.INIT,
    currentPage: 1,
    pageSize: UserStorage.getInstance().getAdminPageConfigFromLocalStore().securityMenuPageSize,
    total: 0,
    totalPage: 10
};

export const securityMenuSlice = createSlice({
    name: 'securityMenu',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builderPagingHandler(builder, fetchSecurityMenuPageData);
        /** 添加菜单信息信息 */
        builder.addCase(fetchAddSecurityMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 修改菜单信息信息 */
        builder.addCase(fetchUpdateSecurityMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
        /** 菜单信息用户信息 */
        builder.addCase(fetchDeleteSecurityMenu.fulfilled, (state, action) => {
            state.requestStatus = RequestStatus.REFRESH;
        });
    }
});

export const {} = securityMenuSlice.actions;
export default securityMenuSlice.reducer;

