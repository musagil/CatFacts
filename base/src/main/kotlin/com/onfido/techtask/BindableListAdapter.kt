package com.onfido.techtask

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindableListAdapter<D : com.onfido.techtask.BindableItem, VH : RecyclerView.ViewHolder>(
    callback: DiffUtil.ItemCallback<D> = com.onfido.techtask.DiffUtilCallback()
) : ListAdapter<D, VH>(callback)
