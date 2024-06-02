import {Space} from "antd";
import {ColumnsType} from "antd/es/table";
import {Link} from "react-router-dom";
import {SecurityMenu} from "type/entity/SecurityMenu";
import {columnDataIndex, columnOrder, columnRemark} from "./index";


const securityMenuColumns: ColumnsType<SecurityMenu> = [
    columnDataIndex,
    {
        key: 'id',
        dataIndex: 'id',
        title: '自增主键',
        width: 110,
        align: "center",
    },
    {
        key: 'key',
        dataIndex: 'key',
        title: '数据唯一标识',
        width: 110,
        align: "center",
    },
    {
        key: 'parentId',
        dataIndex: 'parentId',
        title: '菜单的上一级菜单ID',
        width: 110,
        align: "center",
    },
    {
        key: 'label',
        dataIndex: 'label',
        title: '菜单标签(名字)',
        width: 110,
        align: "center",
    },
    {
        key: 'describe',
        dataIndex: 'describe',
        title: '菜单功能描述',
        width: 110,
        align: "center",
    },
    {
        key: 'path',
        dataIndex: 'path',
        title: '菜单路径地址',
        width: 110,
        align: "center",
    },
    {
        key: 'icon',
        dataIndex: 'icon',
        title: '菜单图标',
        width: 110,
        align: "center",
    },
    {
        key: 'last',
        dataIndex: 'last',
        title: '菜单是否是最后一级菜单',
        width: 110,
        align: "center",
    },
    {
        key: 'menuOrder',
        dataIndex: 'menuOrder',
        title: '菜单排序字段',
        width: 110,
        align: "center",
    },
    {
        key: 'show',
        dataIndex: 'show',
        title: '是否要显示菜单标志',
        width: 110,
        align: "center",
    },
    {
        key: 'createTime',
        dataIndex: 'createTime',
        title: '创建时间',
        width: 110,
        align: "center",
    },
    {
        key: 'updateTime',
        dataIndex: 'updateTime',
        title: '修改时间',
        width: 110,
        align: "center",
    },
    {
        key: 'delete',
        dataIndex: 'delete',
        title: '逻辑删除字段',
        width: 110,
        align: "center",
    },
    {
        key: 'version',
        dataIndex: 'version',
        title: '数据版本',
        width: 110,
        align: "center",
    },
    {
        key: 'order',
        dataIndex: 'order',
        title: '数据排序字段',
        width: 110,
        align: "center",
    },
    {
        key: 'remark',
        dataIndex: 'remark',
        title: '数据备注信息',
        width: 110,
        align: "center",
    },
    columnOrder,
    columnRemark,

    {
        key: "options",
        title: "操作",
        dataIndex: "options",
        align: "center",
        width: 130,
        fixed: "right",
        render: (_, record, __) => {
            return <Space size={15}>
                <Link key={"securityMenu-details"} to={"details"} state={record}>详情</Link>
                <Link key={"securityMenu-update"} to={"update"} state={record}>更新</Link>
                <Link key={"securityMenu-delete"} style={{color: "#c13c3c"}} to={"delete"} state={record}>删除</Link>
            </Space>;
        }
    }

];
