package edu.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.Date;
import java.util.List;
import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Device;
import edu.ualr.recyclerviewassignment.model.Item;
import edu.ualr.recyclerviewassignment.model.SectionHeader;

public class AdapterListBasic extends RecyclerView.Adapter
{
    private static final int DEVICE_VIEW = 0;
    private static final int SECTION_VIEW = 1;
    private Context context;
    private SortedList<Item> items;
    private OnItemClickListener mOnItemClickListener;

    public AdapterListBasic(Context context, List<Item> items)
    {
        this.context = context;
        this.items = new SortedList<>(Item.class, new SortedList.Callback<Item>() {

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(Item o1, Item o2) {
                if (o1.isSection() && !o2.isSection()) {
                    if (((SectionHeader) o1).getHeader().ordinal() <= ((Device) o2).getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else if (!o1.isSection() && o2.isSection()) {
                    if (((Device) o1).getDeviceStatus().ordinal() < ((SectionHeader) o2).getHeader().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else if ((!o1.isSection() && !o2.isSection())) {
                    if (((Device) o1).getDeviceStatus().ordinal() < ((Device) o2).getDeviceStatus().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else if ((o1.isSection() && o2.isSection())) {
                    if (((SectionHeader) o1).getHeader().ordinal() < ((SectionHeader) o2).getHeader().ordinal()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                else {
                    return 0;
                }
            }

            @Override
            public void onChanged(int position, int count) {
                notifyDataSetChanged();
            }

            @Override
            public boolean areContentsTheSame(Item oldItem, Item newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(Item item1, Item item2) {
                return false;
            }
        });
        this.items.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder vh;
        View itemView;
        switch (viewType)
        {
            case SECTION_VIEW:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_layout, parent, false);
                vh = new SectionHeaderViewHolder(itemView);
                break;
            case DEVICE_VIEW:
                default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout, parent, false);
                vh = new DeviceViewHolder(itemView);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        final Item item = items.get(position);
        if (holder instanceof DeviceViewHolder)
        {
            final DeviceViewHolder viewHolder = (DeviceViewHolder) holder;
            final Device device = (Device) item;

            viewHolder.device_name.setText(device.getName());

            TypedArray device_images = context.getResources().obtainTypedArray(R.array.device_images);
            viewHolder.device_image.setImageResource(device_images.getResourceId(device.getDeviceType().ordinal(), -1));
            device_images.recycle();

            viewHolder.device_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_schedule_24px, 0, 0, 0);

            updateDeviceStatus(device, viewHolder);


            viewHolder.layout_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, items.get(position), position);
                }
            });

            viewHolder.connection_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, items.get(position), position);
                    if (device.getDeviceStatus() == Device.DeviceStatus.Connected) {
                        device.setDeviceStatus(Device.DeviceStatus.Ready);
                        viewHolder.connection_image.setImageResource(R.drawable.ic_btn_connect);
                    }
                    else {
                        device.setDeviceStatus(Device.DeviceStatus.Connected);
                        viewHolder.connection_image.setImageResource(R.drawable.ic_btn_disconnect);
                    }
                    device.setLastConnection(new Date());
                    updateDeviceStatus(device, viewHolder);
                    items.updateItemAt(position, item);
                }
            });
        }
        else
        {
            final SectionHeaderViewHolder viewHolder = (SectionHeaderViewHolder) holder;
            final SectionHeader sectionHeader = (SectionHeader) item;

            viewHolder.section_header.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.section_header.setText(sectionHeader.getLabel());

            viewHolder.layout_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, items.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isSection() ? SECTION_VIEW : DEVICE_VIEW;
    }

    private void updateDeviceStatus(Device device, DeviceViewHolder viewHolder)
    {
        switch (device.getDeviceStatus())
        {
            case Connected:
                viewHolder.device_image_background.setImageResource(R.drawable.thumbnail_background_solid);
                viewHolder.device_image.setColorFilter(ContextCompat.getColor(context, R.color.grey_5), android.graphics.PorterDuff.Mode.SRC_IN);
                viewHolder.device_image_status.setImageResource(R.drawable.status_mark_connected);
                viewHolder.device_image_status.setVisibility(View.VISIBLE);
                viewHolder.device_status.setText(R.string.currently_connected);
                viewHolder.connection_image.setImageResource(R.drawable.ic_btn_disconnect);
                viewHolder.connection_image.setColorFilter(ContextCompat.getColor(context, R.color.grey_60), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case Ready:
                viewHolder.device_image_background.setImageResource(R.drawable.thumbnail_background_solid);
                viewHolder.device_image.setColorFilter(ContextCompat.getColor(context, R.color.grey_5), android.graphics.PorterDuff.Mode.SRC_IN);
                viewHolder.device_image_status.setImageResource(R.drawable.status_mark_ready);
                viewHolder.device_image_status.setVisibility(View.VISIBLE);
                if (device.getLastConnection() != null)
                    viewHolder.device_status.setText(R.string.recently);
                else
                    viewHolder.device_status.setText(R.string.never_connected);
                viewHolder.connection_image.setImageResource(R.drawable.ic_btn_connect);
                viewHolder.connection_image.setColorFilter(ContextCompat.getColor(context, R.color.grey_60), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case Linked:
            default:
                viewHolder.device_image_background.setImageResource(R.drawable.thumbnail_background_wire);
                viewHolder.device_image.setColorFilter(ContextCompat.getColor(context, R.color.grey_40), android.graphics.PorterDuff.Mode.SRC_IN);
                viewHolder.device_image_status.setVisibility(View.INVISIBLE);
                if (device.getLastConnection() != null)
                    viewHolder.device_status.setText(R.string.recently);
                else
                    viewHolder.device_status.setText(R.string.never_connected);
                viewHolder.connection_image.setVisibility(View.INVISIBLE);
                break;
        }
    }



    public class DeviceViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView device_image_background;
        private ImageView device_image;
        private ImageView device_image_status;
        private TextView device_name;
        private TextView device_status;
        private ImageView connection_image;
        private View layout_parent;

        DeviceViewHolder(View v) {
            super(v);
            device_image_background = v.findViewById(R.id.device_image_background);
            device_image = v.findViewById(R.id.device_image);
            device_image_status = v.findViewById(R.id.device_image_status);
            device_name = v.findViewById(R.id.device_name);
            device_status = v.findViewById(R.id.device_status);
            connection_image = v.findViewById(R.id.connection_image);
            layout_parent = v;
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder
    {
        private TextView section_header;
        private View layout_parent;

        SectionHeaderViewHolder(View v) {
            super(v);
            section_header = v.findViewById(R.id.section_header);
            layout_parent = v;
        }
    }




    public interface OnItemClickListener {
        void onItemClick(View view, Item item, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
}
