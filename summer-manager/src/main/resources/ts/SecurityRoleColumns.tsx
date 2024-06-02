import {Space} from "antd";
import {ColumnsType} from "antd/es/table";
import {Link} from "react-router-dom";
import {SecurityRole} from "type/entity/SecurityRole";
import {columnDataIndex, columnOrder, columnRemark} from "./index";


const securityRoleColumns: ColumnsType<SecurityRole> = [
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
        key: 'authority',
        dataIndex: 'authority',
        title: '角色定义',
        width: 110,
        align: "center",
    },
    {
        key: 'icon',
        dataIndex: 'icon',
        title: '角色图标',
        width: 110,
        align: "center",
    },
    {
        key: 'explain',
        dataIndex: 'explain',
        title: '角色说明',
        width: 110,
        align: "center",
    },
    {
        key: 'weight',
        dataIndex: 'weight',
        title: '角色权重',
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
                <Link key={"securityRole-details"} to={"details"} state={record}>详情</Link>
                <Link key={"securityRole-update"} to={"update"} state={record}>更新</Link>
                <Link key={"securityRole-delete"} style={{color: "#c13c3c"}} to={"delete"} state={record}>删除</Link>
            </Space>;
        }
    }

];
